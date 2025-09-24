package ee.qrent.billing.bonus.core.service;

import static ee.qrent.billing.transaction.api.in.utils.TransactionKindCodesConstant.TRANSACTION_KIND_POSITIVE_CODE;
import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeCodesConstant.*;
import static java.math.BigDecimal.ZERO;

import ee.qrent.billing.bonus.api.out.ObligationLoadPort;
import ee.qrent.billing.bonus.domain.Obligation;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrent.billing.transaction.api.in.query.filter.DriverAndPeriodAndKindCodesFilter;
import ee.qrent.billing.transaction.api.in.query.filter.DriverAndPeriodAndTypeCodesFilter;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObligationCalculator {

  private static final BigDecimal DEBT_RATE = BigDecimal.valueOf(0.25d);

  private final ObligationLoadPort loadPort;
  private final GetQWeekQuery qWeekQuery;
  private final GetBalanceQuery balanceQuery;
  private final GetDriverQuery driverQuery;
  private final GetTransactionQuery transactionQuery;

  public Obligation getObligation(final Long driverId, final QWeekResponse qWeek) {
    final var qWeekId = qWeek.getId();
    final var monday = qWeek.getStart();
    final var sunday = qWeek.getEnd();

    return getObligationByDriverIdAndQWeekIdAndPeriod(driverId, qWeekId, monday, sunday);
  }

  public Obligation getObligationOnThursday(final Long driverId, final QWeekResponse qWeek) {
    final var qWeekId = qWeek.getId();
    final var monday = qWeek.getStart();
    final var thursday = qWeek.getEnd().minusDays(3);

    return getObligationByDriverIdAndQWeekIdAndPeriod(driverId, qWeekId, monday, thursday);
  }

  private Obligation getObligationByDriverIdAndQWeekIdAndPeriod(
      final Long driverId,
      final Long requestedQWeekId,
      final LocalDate startDate,
      final LocalDate endDate) {

    final var obligationAmount =
        getObligationAmount(driverId, requestedQWeekId, startDate, endDate);
    final var positiveAmount = getPositiveAmount(driverId, startDate, endDate);

    final var previousWeek = qWeekQuery.getOneBeforeById(requestedQWeekId);
    final var previousWeekId = previousWeek.getId();
    final var matchCount =
        getMatchCount(
            driverId, startDate, endDate, previousWeekId, obligationAmount, positiveAmount);

    return Obligation.builder()
        .id(null)
        .qWeekId(requestedQWeekId)
        .driverId(driverId)
        .obligationAmount(obligationAmount)
        .positiveAmount(positiveAmount)
        .matchCount(matchCount)
        .build();
  }

  private Integer getMatchCount(
      final Long driverId,
      final LocalDate startDate,
      final LocalDate endDate,
      final Long previousQWeekId,
      final BigDecimal obligationAmount,
      final BigDecimal positiveAmount) {

    final var filter =
        DriverAndPeriodAndTypeCodesFilter.builder()
            .driverId(driverId)
            .dateStart(startDate)
            .dateEnd(endDate)
            .typeCodes(
                Stream.of(
                        TRANSACTION_TYPE_NAME_WEEKLY_RENT_CODE, TRANSACTION_TYPE_NO_LABEL_FINE_CODE)
                    .collect(Collectors.toSet()))
            .build();
    final var rentTransactionCount = transactionQuery.getAllByFilter(filter).stream().count();
    if (rentTransactionCount == 0) {
      System.out.println("No Rent transactions. Match count is O");

      return 0;
    }
    if (positiveAmount.compareTo(obligationAmount) >= 0) {
      final var previousWeekObligation =
          loadPort.loadByDriverIdAndByQWeekId(driverId, previousQWeekId);
      if (previousWeekObligation == null) {

        return 0;
      }
      var previousWeekObligationMatchCount = previousWeekObligation.getMatchCount();

      return ++previousWeekObligationMatchCount;
    }

    return 0;
  }

  private BigDecimal getPositiveAmount(
      final Long driverId, final LocalDate startDate, final LocalDate endDate) {
    final var filter =
        DriverAndPeriodAndKindCodesFilter.builder()
            .driverId(driverId)
            .dateStart(startDate)
            .dateEnd(endDate)
            .kindCodes(Stream.of(TRANSACTION_KIND_POSITIVE_CODE).collect(Collectors.toSet()))
            .build();

    return transactionQuery.getAllByFilter(filter).stream()
        .map(TransactionResponse::getRealAmount)
        .reduce(ZERO, BigDecimal::add);
  }

  public BigDecimal getObligationAmount(
      final Long driverId, final Long qWeekId, final LocalDate startDate, final LocalDate endDate) {
    var obligationAmount = ZERO;

    final var previousQWeek = qWeekQuery.getOneBeforeById(qWeekId);
    final var previousQWeekId = previousQWeek.getId();
    final var initialObligationAmount = getInitialObligationAmount(driverId, startDate, endDate);
    final var initialObligationAmountAbs = initialObligationAmount.abs();
    final var previousWeekBalance =
        balanceQuery
            .getRawContextByDriverIdAndQWeekId(driverId, previousQWeekId)
            .getRequestedWeekBalance()
            .getAmount();
    if (previousWeekBalance.compareTo(ZERO) < 0) {
      final var debt = getDebt(previousWeekBalance, initialObligationAmountAbs);
      obligationAmount = initialObligationAmountAbs.add(debt);
    } else if (previousWeekBalance.compareTo(ZERO) > 0) {
      obligationAmount = initialObligationAmountAbs.subtract(previousWeekBalance.abs());
    } else {
      obligationAmount = initialObligationAmountAbs;
    }
    final var requiredObligation = getRequiredObligation(driverId);
    if (requiredObligation.compareTo(obligationAmount) > 0) {

      return requiredObligation;
    }

    return obligationAmount;
  }

  private BigDecimal getDebt(
      final BigDecimal previousWeekBalance, final BigDecimal rentObligationAbs) {
    final var debtNominal = rentObligationAbs.multiply(DEBT_RATE);
    final var previousWeekBalanceAbs = previousWeekBalance.abs();

    if (debtNominal.compareTo(previousWeekBalanceAbs) > 0) {
      return previousWeekBalanceAbs;
    }
    return debtNominal;
  }

  private BigDecimal getRequiredObligation(final Long driverId) {
    final var driver = driverQuery.getById(driverId);
    if (driver.getHasRequiredObligation()) {
      return driver.getRequiredObligation();
    }
    return ZERO;
  }

  private BigDecimal getInitialObligationAmount(
      final Long driverId, final LocalDate startDate, final LocalDate endDate) {
    final var filter =
        DriverAndPeriodAndTypeCodesFilter.builder()
            .driverId(driverId)
            .dateStart(startDate)
            .dateEnd(endDate)
            .typeCodes(
                Stream.of(
                        TRANSACTION_TYPE_NAME_WEEKLY_RENT_CODE,
                        TRANSACTION_TYPE_NO_LABEL_FINE_CODE,
                        TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_CODE)
                    .collect(Collectors.toSet()))
            .build();

    return transactionQuery.getAllByFilter(filter).stream()
        .map(TransactionResponse::getRealAmount)
        .reduce(ZERO, BigDecimal::add);
  }
}
