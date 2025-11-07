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

  private final GetBalanceQuery balanceQuery;
  private final ObligationLoadPort loadPort;
  private final GetQWeekQuery qWeekQuery;
  private final GetDriverQuery driverQuery;
  private final GetTransactionQuery transactionQuery;

  public Obligation getObligationOnThursday(final Long driverId, final QWeekResponse qWeek) {
    final var qWeekId = qWeek.getId();
    final var monday = qWeek.getStart();
    final var thursday = qWeek.getEnd().minusDays(3);

    return getObligationByDriverIdAndQWeekIdAndPeriod(driverId, qWeekId, monday, thursday);
  }

  private Obligation getObligationByDriverIdAndQWeekIdAndPeriod(
      final Long driverId,
      final Long requestedQWeekId,
      final LocalDate monday,
      final LocalDate thursday) {

    final var obligationAmount = getObligationAmount(driverId, monday, thursday);
    final var positiveAmount = getPositiveAmount(driverId, monday, thursday);

    final var previousWeek = qWeekQuery.getOneBeforeById(requestedQWeekId);
    final var previousWeekId = previousWeek.getId();
    final var matchCount =
        getMatchCount(
            driverId, monday, thursday, previousWeekId, obligationAmount, positiveAmount);

    return Obligation.builder()
        .id(null)
        .qWeekId(requestedQWeekId)
        .driverId(driverId)
        .obligationAmount(obligationAmount)
        .positiveAmount(positiveAmount)
        .matchCount(matchCount)
        .build();
  }

  public BigDecimal getObligationAmount(
      final Long driverId, final LocalDate monday, final LocalDate thursday) {
    final var manualObligationAmount = getManualObligation(driverId);
    final var automaticObligationAmount = getAutomaticObligationAmount(driverId, monday, thursday);
    if (manualObligationAmount.compareTo(automaticObligationAmount) > 0) {

      return manualObligationAmount;
    }
    final var sunday = monday.minusDays(1);
    final var sundayRawBalance = balanceQuery.getRawByDriverAndDate(driverId, sunday);
    final var sundayRawBalanceAmount = sundayRawBalance.getAmount();
    if (sundayRawBalanceAmount.compareTo(ZERO) >= 0) {
     final var overpayment = sundayRawBalanceAmount.subtract(automaticObligationAmount);
     if(overpayment.compareTo(ZERO) >=0){

       return ZERO;
     }

      return overpayment.abs();
    }
    final var extraAmount = automaticObligationAmount.multiply(DEBT_RATE);
    if (sundayRawBalanceAmount.abs().compareTo(extraAmount) >= 0) {

      return automaticObligationAmount.add(extraAmount);
    }

    return automaticObligationAmount.add(sundayRawBalanceAmount);
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
    final var rentTransactionCount = (long) transactionQuery.getAllByFilter(filter).size();
    if (rentTransactionCount == 0) {
      System.out.println("No Rent transactions. Match count is O");

      return 0;
    }
    if (positiveAmount.compareTo(obligationAmount) >= 0) {
      final var previousWeekObligation =
          loadPort.loadByDriverIdAndByQWeekId(driverId, previousQWeekId);
      if (previousWeekObligation == null) {

        return 1;
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

  private BigDecimal getManualObligation(final Long driverId) {
    final var driver = driverQuery.getById(driverId);
    if (driver.getHasRequiredObligation()) {
      return driver.getRequiredObligation();
    }
    return ZERO;
  }

  private BigDecimal getAutomaticObligationAmount(
      final Long driverId, final LocalDate startDate, final LocalDate endDate) {
    final var filter =
        DriverAndPeriodAndTypeCodesFilter.builder()
            .driverId(driverId)
            .dateStart(startDate)
            .dateEnd(endDate)
            .typeCodes(
                Stream.of(
                        TRANSACTION_TYPE_NAME_WEEKLY_RENT_CODE,
                        TRANSACTION_TYPE_NO_LABEL_FINE_CODE)
                    .collect(Collectors.toSet()))
            .build();

    return transactionQuery.getAllByFilter(filter).stream()
        .map(TransactionResponse::getRealAmount)
        .reduce(ZERO, BigDecimal::add)
        .abs();
  }
}
