package ee.qrental.transaction.core.service.balance;

import static ee.qrental.common.core.utils.QNumberUtils.round;
import static ee.qrental.transaction.core.service.balance.calculator.BalanceCalculatorStrategy.DRY_RUN;
import static java.lang.Boolean.TRUE;
import static java.math.BigDecimal.ZERO;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import ee.qrental.common.core.utils.QTimeUtils;
import ee.qrental.constant.api.in.query.GetConstantQuery;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodAndKindAndDriverTransactionFilter;
import ee.qrental.transaction.api.in.query.kind.GetTransactionKindQuery;
import ee.qrental.transaction.api.in.response.balance.BalanceResponse;
import ee.qrental.transaction.api.in.response.kind.TransactionKindResponse;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.core.mapper.balance.BalanceResponseMapper;
import ee.qrental.transaction.core.service.balance.calculator.BalanceCalculatorStrategy;
import ee.qrental.transaction.domain.Transaction;
import ee.qrental.transaction.domain.balance.Balance;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import ee.qrental.transaction.domain.kind.TransactionKindsCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceQueryService implements GetBalanceQuery {

  private final GetDriverQuery driverQuery;
  private final GetQWeekQuery qWeekQuery;
  private final GetConstantQuery constantQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetTransactionKindQuery transactionKindQuery;
  private final BalanceLoadPort balanceLoadPort;
  private final TransactionLoadPort transactionLoadPort;
  private final BalanceResponseMapper balanceResponseMapper;
  private final List<BalanceCalculatorStrategy> calculatorStrategies;

  private BalanceCalculatorStrategy getDryRunStrategy() {
    return calculatorStrategies.stream()
        .filter(strategy -> strategy.canApply(DRY_RUN))
        .findFirst()
        .get();
  }

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
  public BalanceResponse getRawBalanceByDriver(final Long driverId) {
    final var driver = driverQuery.getById(driverId);
    var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);
    final var rawWeeks = getRawQWeeks(latestBalance);
    final var calculator = getDryRunStrategy();

    for (final QWeekResponse week : rawWeeks) {
      final var weekTransactions =
          transactionQuery.getAllByDriverIdAndQWeekId(driverId, week.getId()).stream()
              .collect(
                  groupingBy(
                      transactionResponse ->
                          TransactionKindsCode.valueOf(transactionResponse.getKind())));
      latestBalance = calculator.calculateBalance(driver, week, latestBalance, weekTransactions);
    }

    final var response = balanceResponseMapper.toResponse(latestBalance);

    return response;
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
  public BalanceResponse getLatestByDriverId(final Long driverId) {
    final var latestWeek = qWeekQuery.getCurrentWeek();
    return getByDriverIdAndQWeekId( driverId, latestWeek.getId());
  }

  @Override
  public BigDecimal getRawBalanceTotalByDriver(final Long driverId) {
    final var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);
    final var balanceSum =
        latestBalance != null ? latestBalance.getAmountsSumWithoutRepairment() : ZERO;
    final var rawSum =
        getRawTransactionsSum(latestBalance, driverId, getNonRepairmentTransactionKindIds());

    return round(balanceSum.add(rawSum));
  }

  @Override
  public BigDecimal getRawRepairmentTotalByDriver(final Long driverId) {
    final var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);
    final var repairmentBalanceSum =
        latestBalance != null ? latestBalance.getRepairmentAmount() : ZERO;
    final var repairmentTransactionKinds = transactionKindQuery.getAllRepairment();
    final var repairmentTransactionKindIds =
        repairmentTransactionKinds.stream().map(TransactionKindResponse::getId).toList();

    final var rawSum = getRawTransactionsSum(latestBalance, driverId, repairmentTransactionKindIds);

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

      return round(balance.getAmountsSumWithoutRepairment());
    }
    final var endDate = QTimeUtils.getLastDayOfWeekInYear(year, weekNumber);
    final var filterBuilder =
        PeriodAndKindAndDriverTransactionFilter.builder().dateEnd(endDate).driverId(driverId);
    final var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);
    if (latestBalance == null) {
      final var startDate = QTimeUtils.getFirstDayOfYear(year);
      final var filter =
          filterBuilder
              .dateStart(startDate)
              .transactionKindIds(getNonRepairmentTransactionKindIds())
              .build();

      return getSumOfTransactionByFilter(filter);
    }
    final var latestCalculatedWeek = qWeekQuery.getById(latestBalance.getQWeekId());
    final var latestCalculatedWeekNumber = latestCalculatedWeek.getNumber();

    final var earliestNotCalculatedWeek = latestCalculatedWeekNumber + 1;
    final var startDate = QTimeUtils.getFirstDayOfWeekInYear(year, earliestNotCalculatedWeek);
    final var filter =
        filterBuilder
            .dateStart(startDate)
            .transactionKindIds(getNonRepairmentTransactionKindIds())
            .build();
    final var fromBalance = latestBalance.getAmountsSumWithoutRepairment();
    final var rawTransactionSum = getSumOfTransactionByFilter(filter);

    return round(fromBalance.add(rawTransactionSum));
  }

  @Override
  public BigDecimal getRawBalanceTotalByDriverIdAndQWeekId(
      final Long driverId, final Long qWeekId) {
    final var balance = balanceLoadPort.loadByDriverIdAndQWeekIdAndDerived(driverId, qWeekId, true);
    if (balance != null) {

      return round(balance.getAmountsSumWithoutRepairment());
    }
    final var qWeek = qWeekQuery.getById(qWeekId);
    final var endDate = qWeek.getEnd();
    final var filterBuilder =
        PeriodAndKindAndDriverTransactionFilter.builder().dateEnd(endDate).driverId(driverId);
    final var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);
    if (latestBalance == null) {
      final var startDate = QTimeUtils.getFirstDayOfYear(qWeekQuery.getFirstWeek().getYear());
      final var filter =
          filterBuilder
              .dateStart(startDate)
              .transactionKindIds(getNonRepairmentTransactionKindIds())
              .build();

      return getSumOfTransactionByFilter(filter);
    }
    final var latestCalculatedQWeek = qWeekQuery.getById(latestBalance.getQWeekId());
    final var earliestNotCalculatedWeek = qWeekQuery.getOneAfterById(latestCalculatedQWeek.getId());
    final var startDate = earliestNotCalculatedWeek.getStart();
    final var filter =
        filterBuilder
            .dateStart(startDate)
            .transactionKindIds(getNonRepairmentTransactionKindIds())
            .build();
    final var fromBalance = latestBalance.getAmountsSumWithoutRepairment();
    final var rawTransactionSum = getSumOfTransactionByFilter(filter);

    return round(fromBalance.add(rawTransactionSum));
  }

  @Override
  public Long getCountByDriver(final Long driverId) {
    return balanceLoadPort.loadCountByDriver(driverId);
  }

  private BigDecimal getFeeFromBalanceOrDefault(final Balance latestBalance) {
    if (latestBalance != null) {
      return latestBalance.getFeeAmount();
    }
    return ZERO;
  }

  private BigDecimal getFeeAbleFromBalanceOrDefault(final Balance latestBalance) {
    if (latestBalance != null) {

      return latestBalance.getFeeAbleAmount();
    }

    return ZERO;
  }

  private List<QWeekResponse> getRawQWeeks(final Balance latestBalance) {
    final var currentQWeek = qWeekQuery.getCurrentWeek();
    if (latestBalance == null) {
      final var firstQWeek = qWeekQuery.getFirstWeek();

      return qWeekQuery.getAllBetweenByIdsDefaultOrder(firstQWeek.getId(), currentQWeek.getId());
    }
    final var firstRawQWeek = qWeekQuery.getOneAfterById(latestBalance.getQWeekId());

    return qWeekQuery.getAllBetweenByIdsDefaultOrder(firstRawQWeek.getId(), currentQWeek.getId());
  }

  @Override
  public BigDecimal getRawFeeTotalByDriver(final Long driverId) {
    final var driver = driverQuery.getById(driverId);
    if (!driver.getNeedFee()) {

      return ZERO;
    }

    final var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);
    var feeTotal = getFeeFromBalanceOrDefault(latestBalance);
    var feeAbleTotal = getFeeAbleFromBalanceOrDefault(latestBalance);
    final var rawQWeeks = getRawQWeeks(latestBalance);
    final var weeklyInterest = constantQuery.getFeeWeeklyInterest();

    for (final QWeekResponse week : rawQWeeks) {
      final var weekTotal =
          transactionLoadPort
              .loadAllByDriverIdAndKindIdAndBetweenDays(
                  driverId,
                  getAllNonRepairmentAndNonFeeAbleTransactionKindIds(),
                  week.getStart(),
                  week.getEnd())
              .stream()
              .map(Transaction::getRealAmount)
              .reduce(ZERO, BigDecimal::add);
      feeAbleTotal = feeAbleTotal.add(weekTotal);
      if (feeAbleTotal.compareTo(ZERO) < 0) {
        final var weekFee = feeAbleTotal.multiply(weeklyInterest);
        feeTotal = feeTotal.add(weekFee);
        if (feeTotal.compareTo(feeAbleTotal) <= 0) {
          feeTotal = feeAbleTotal;
        }
      }
    }

    return round(feeTotal);
  }

  private BigDecimal getRawTransactionsSum(
      final Balance latestBalance, final Long driverId, final List<Long> transactionKindIds) {
    final var earliestNotCalculatedDate = getEarliestNotCalculatedDate(latestBalance);
    final var rawTransactionFilter =
        PeriodAndKindAndDriverTransactionFilter.builder()
            .driverId(driverId)
            .dateStart(earliestNotCalculatedDate)
            .dateEnd(LocalDate.now())
            .transactionKindIds(transactionKindIds)
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
  public BigDecimal getFeeByDriver(final Long driverId) {
    final var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);

    return latestBalance != null ? round(latestBalance.getFeeAmount()) : ZERO;
  }

  @Override
  public BalanceResponse getLatestCalculatedBalanceByDriver(final Long driverId) {
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
            .transactionKindIds(getNonRepairmentTransactionKindIds())
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
        transactionLoadPort.loadAllByDriverIdAndKindIdAndBetweenDays(
            filter.getDriverId(),
            filter.getTransactionKindIds(),
            filter.getDateStart(),
            filter.getDateEnd());
    final var result =
        rawTransactions.stream().map(Transaction::getRealAmount).reduce(ZERO, BigDecimal::add);

    return round(result);
  }

  private List<Long> getNonRepairmentTransactionKindIds() {
    final var nonRepairmentTransactionKinds = transactionKindQuery.getAllNonRepairment();
    return nonRepairmentTransactionKinds.stream().map(TransactionKindResponse::getId).toList();
  }

  private List<Long> getAllNonRepairmentAndNonFeeAbleTransactionKindIds() {
    final var nonRepairmentAndNonFeeAbleTransactionKinds =
        transactionKindQuery.getAllNonRepairmentExceptNonFeeAble();
    return nonRepairmentAndNonFeeAbleTransactionKinds.stream()
        .map(TransactionKindResponse::getId)
        .toList();
  }
}
