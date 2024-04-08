package ee.qrental.transaction.core.service.balance;

import static ee.qrental.common.core.utils.QNumberUtils.round;
import static ee.qrental.transaction.core.utils.FeeUtils.FEE_WEEKLY_INTEREST;
import static ee.qrental.transaction.core.utils.FeeUtils.getWeekFeeInterest;
import static java.lang.Boolean.TRUE;
import static java.math.BigDecimal.ZERO;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import ee.qrental.common.core.utils.QTimeUtils;
import ee.qrental.common.core.utils.QWeekIterator;
import ee.qrental.constant.api.in.query.GetConstantQuery;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodAndKindAndDriverTransactionFilter;
import ee.qrental.transaction.api.in.response.balance.BalanceResponse;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.core.mapper.balance.BalanceResponseMapper;
import ee.qrental.transaction.domain.Transaction;
import ee.qrental.transaction.domain.balance.Balance;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceQueryService implements GetBalanceQuery {

  private final GetQWeekQuery qWeekQuery;
  private final GetConstantQuery constantQuery;
  private final BalanceLoadPort balanceLoadPort;
  private final TransactionLoadPort transactionLoadPort;
  private final BalanceResponseMapper balanceResponseMapper;

  @Override
  public BalanceResponse getLatestBalanceByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber) {
    final var latestBalance =
        balanceLoadPort.loadLatestByDriverIdAndYearAndWeekNumber(driverId, year, weekNumber);

    return balanceResponseMapper.toResponse(latestBalance);
  }

  @Override
  public BigDecimal getFeeByDriverIdAndQWeekId(final Long driverId, final Long qWeekId) {
    final var balance = balanceLoadPort.loadByDriverIdAndQWeekIdAndDerived(driverId, qWeekId, true);

    return balance == null ? ZERO : balance.getFeeAmount();
  }

  @Override
  public List<BalanceResponse> getAll() {
    return balanceLoadPort.loadAll().stream()
        .map(balanceResponseMapper::toResponse)
        .collect(toList());
  }

  @Override
  public BalanceResponse getById(final Long id) {
    return balanceResponseMapper.toResponse(balanceLoadPort.loadById(id));
  }

  @Override
  public BalanceResponse getByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber) {
    return balanceResponseMapper.toResponse(
        balanceLoadPort.loadByDriverIdAndYearAndWeekNumberOrDefault(driverId, year, weekNumber));
  }

  @Override
  public BalanceResponse getByDriverIdAndQWeekId(final Long driverId, final Long qWeekId) {
    return balanceResponseMapper.toResponse(
        balanceLoadPort.loadByDriverIdAndQWeekIdAndDerived(driverId, qWeekId, true));
  }

  @Override
  public BigDecimal getRawBalanceTotalByDriver(final Long driverId) {
    final var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);
    final var balanceSum = latestBalance != null ? latestBalance.getAmount() : ZERO;
    final var rawSum =
        getRawTransactionsSum(latestBalance, driverId, asList("F", "NFA", "FA", "P"));

    return round(balanceSum.add(rawSum));
  }

  @Override
  public BigDecimal getRawRepairmentTotalByDriver(final Long driverId) {
    final var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);
    final var repairmentBalanceSum =
        latestBalance != null ? latestBalance.getRepairmentAmount() : ZERO;
    final var rawSum = getRawTransactionsSum(latestBalance, driverId, asList("R"));

    return round(repairmentBalanceSum.add(rawSum));
  }

  @Override
  public BigDecimal getRawRepairmentTotalByDriverWithQKasko(final Long driverId) {

    final var totalRepairment = getRawRepairmentTotalByDriver(driverId).abs();
    final var selfResponsibility = BigDecimal.valueOf(500);
    if (totalRepairment.compareTo(ZERO) == 0) {
      return ZERO;
    }
    if (totalRepairment.compareTo(selfResponsibility) < 0) {
      return totalRepairment;
    }

    final var sharedResponsibility = totalRepairment.subtract(selfResponsibility);
    final var driversResponsibilityShare = sharedResponsibility.divide(BigDecimal.valueOf(2));
    final var totalDriverResponsibility = driversResponsibilityShare.add(selfResponsibility);

    return totalDriverResponsibility.negate();
  }

  @Override
  public BigDecimal getRawBalanceTotalByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber) {
    final var balance =
        balanceLoadPort.loadByDriverIdAndYearAndWeekNumberOrDefault(driverId, year, weekNumber);
    if (balance != null) {

      return round(balance.getAmount());
    }

    final var endDate = QTimeUtils.getLastDayOfWeekInYear(year, weekNumber);
    final var filterBuilder =
        PeriodAndKindAndDriverTransactionFilter.builder().dateEnd(endDate).driverId(driverId);
    final var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);

    if (latestBalance == null) {
      final var startDate = QTimeUtils.getFirstDayOfYear(year);
      final var filter = filterBuilder.dateStart(startDate).build();

      return getSumOfTransactionByFilter(filter);
    }
    final var latestCalculatedWeek = qWeekQuery.getById(latestBalance.getQWeekId());
    final var latestCalculatedWeekNumber = latestCalculatedWeek.getNumber();

    final var earliestNotCalculatedWeek = latestCalculatedWeekNumber + 1;
    final var startDate = QTimeUtils.getFirstDayOfWeekInYear(year, earliestNotCalculatedWeek);
    final var filter = filterBuilder.dateStart(startDate).build();
    final var fromBalance = latestBalance.getAmount();
    final var rawTransactionSum = getSumOfTransactionByFilter(filter);

    return round(fromBalance.add(rawTransactionSum));
  }

  @Override
  public BigDecimal getRawBalanceTotalByDriverIdAndQWeekId(
      final Long driverId, final Long qWeekId) {
    final var balance = balanceLoadPort.loadByDriverIdAndQWeekIdAndDerived(driverId, qWeekId, true);
    if (balance != null) {

      return round(balance.getAmount());
    }
    final var qWeek = qWeekQuery.getById(qWeekId);
    final var endDate = qWeek.getEnd();
    final var filterBuilder =
        PeriodAndKindAndDriverTransactionFilter.builder().dateEnd(endDate).driverId(driverId);
    final var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);

    if (latestBalance == null) {
      final var startDate = QTimeUtils.getFirstDayOfYear(qWeekQuery.getFirstWeek().getYear());
      final var filter = filterBuilder.dateStart(startDate).build();

      return getSumOfTransactionByFilter(filter);
    }
    final var latestCalculatedQWeek = qWeekQuery.getById(latestBalance.getQWeekId());
    final var earliestNotCalculatedWeek = qWeekQuery.getOneAfterById(latestCalculatedQWeek.getId());
    final var startDate = earliestNotCalculatedWeek.getStart();
    final var filter =
        filterBuilder
            .dateStart(startDate)
            .transactionKindCodes(asList("F", "NFA", "FA", "P", "R"))
            .build();
    final var fromBalance = latestBalance.getAmount();
    final var rawTransactionSum = getSumOfTransactionByFilter(filter);

    return round(fromBalance.add(rawTransactionSum));
  }

  @Override
  public Long getCountByDriver(final Long driverId) {
    return balanceLoadPort.loadCountByDriver(driverId);
  }

  @Override
  public BigDecimal getRawFeeTotalByDriver(final Long driverId) {
    var rawFeeTotal = ZERO;
    var rawTotal = ZERO;
    var startDate = LocalDate.of(2023, 1, 1);

    final var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);
    if (latestBalance != null) {
      final var feeFromBalance = latestBalance.getFeeAmount();
      rawFeeTotal = rawFeeTotal.add(feeFromBalance);

      final var balanceAmount = latestBalance.getAmount();
      rawTotal = rawTotal.add(balanceAmount);

      final var latestBalanceWeek = qWeekQuery.getById(latestBalance.getQWeekId());
      final var year = latestBalanceWeek.getYear();
      final var latestCalculatedWeekNumbr = latestBalanceWeek.getNumber();
      final var earliestNotCalculatedWeek = latestCalculatedWeekNumbr + 1;
      startDate = QTimeUtils.getFirstDayOfWeekInYear(year, earliestNotCalculatedWeek);
    }

    final var endDate = LocalDate.now();
    final var weekIterator = new QWeekIterator(startDate, endDate);
    final var weeklyInterestConstant = constantQuery.getByName(FEE_WEEKLY_INTEREST);
    final var weeklyInterest = getWeekFeeInterest(weeklyInterestConstant);
    final var nonRepairmentTransactionKindCodes =
        new HashSet<>() {
          {
            add("F");
            add("NFA");
            add("FA");
            add("P");
          }
        };

    while (weekIterator.hasNext()) {
      final var week = weekIterator.next();
      final var weekTransactions =
          transactionLoadPort.loadAllByDriverIdAndBetweenDays(driverId, week.start(), week.end());
      final var weekTotal =
          weekTransactions.stream()
              .filter(
                  transaction ->
                      nonRepairmentTransactionKindCodes.contains(transaction.getType().getKind()))
              .map(Transaction::getRealAmount)
              .reduce(ZERO, BigDecimal::add);
      rawTotal = rawTotal.add(weekTotal);
      if (rawTotal.compareTo(ZERO) < 0) {
        final var weekFee = rawTotal.multiply(weeklyInterest);
        rawFeeTotal = rawFeeTotal.add(weekFee);
        if (rawFeeTotal.compareTo(rawTotal) <= 0) {
          rawFeeTotal = rawTotal;
        }
      }
    }

    return round(rawFeeTotal);
  }

  private BigDecimal getRawTransactionsSum(
      final Balance latestBalance, final Long driverId, final List<String> kindCodes) {
    final var earliestNotCalculatedDate = getEarliestNotCalculatedDate(latestBalance);
    final var rawTransactionFilter =
        PeriodAndKindAndDriverTransactionFilter.builder()
            .driverId(driverId)
            .dateStart(earliestNotCalculatedDate)
            .dateEnd(LocalDate.now())
            .transactionKindCodes(kindCodes)
            .build();

    return getSumOfTransactionByFilter(rawTransactionFilter);
  }

  private LocalDate getEarliestNotCalculatedDate(final Balance latestBalance) {
    if (latestBalance == null) {
      return LocalDate.now().with(firstDayOfYear());
    }
    final var latestBalanceWeek = qWeekQuery.getById(latestBalance.getQWeekId());
    final var latestBalanceYear = latestBalanceWeek.getYear();
    final var latestBalanceWeekNumber = latestBalanceWeek.getNumber();
    final var latestCalculatedDate =
        QTimeUtils.getLastDayOfWeekInYear(latestBalanceYear, latestBalanceWeekNumber);

    return latestCalculatedDate.plusDays(1L);
  }

  @Override
  public BigDecimal getFeeByDriver(Long driverId) {
    final var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);

    return latestBalance != null ? round(latestBalance.getFeeAmount()) : ZERO;
  }

  @Override
  public BalanceResponse getLatestBalanceByDriver(final Long driverId) {
    final var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);

    return balanceResponseMapper.toResponse(latestBalance);
  }

  @Override
  public BalanceResponse getDerivedBalanceByDriverAndQWeek(
      final Long driverId, final Long qWeekId) {
    final var balance = balanceLoadPort.loadByDriverIdAndQWeekIdAndDerived(driverId, qWeekId, TRUE);

    return balanceResponseMapper.toResponse(balance);
  }

  @Override
  public BigDecimal getPeriodAmountByDriverAndQWeek(final Long driverId, final Long qWeekId) {
    final var qWeek = qWeekQuery.getById(qWeekId);

    final var periodFilter =
        PeriodAndKindAndDriverTransactionFilter.builder()
            .driverId(driverId)
            .dateStart(qWeek.getStart())
            .dateEnd(qWeek.getEnd())
            .build();
    final var periodAmount = getSumOfTransactionByFilter(periodFilter);

    return periodAmount;
  }

  @Override
  public BigDecimal getPeriodFeeByDriverAndQWeek(final Long driverId, final Long qWeekId) {
    final var qWeek = qWeekQuery.getById(qWeekId);

    return transactionLoadPort
        .loadAllFeeByDriverIdAndBetweenDays(driverId, qWeek.getStart(), qWeek.getEnd())
        .stream()
        .map(Transaction::getAmount)
        .reduce(ZERO, BigDecimal::add);
  }

  private BigDecimal getSumOfTransactionByFilter(
      final PeriodAndKindAndDriverTransactionFilter filter) {
    final var rawTransactions =
        transactionLoadPort.loadAllByDriverIdAndBetweenDays(
            filter.getDriverId(), filter.getDateStart(), filter.getDateEnd());
    final var result =
        rawTransactions.stream()
            .filter(
                transaction ->
                    filter
                        .getTransactionKindCodes()
                        .contains(transaction.getType().getKind().getCode()))
            .map(Transaction::getRealAmount)
            .reduce(ZERO, BigDecimal::add);

    return round(result);
  }
}
