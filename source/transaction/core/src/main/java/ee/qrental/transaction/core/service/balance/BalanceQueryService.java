package ee.qrental.transaction.core.service.balance;

import static ee.qrental.common.utils.QNumberUtils.qRound;
import static ee.qrental.common.utils.QTimeUtils.*;
import static ee.qrental.transaction.core.service.balance.calculator.BalanceCalculatorStrategy.DRY_RUN;
import static java.math.BigDecimal.ZERO;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodAndKindAndDriverTransactionFilter;
import ee.qrental.transaction.api.in.query.kind.GetTransactionKindQuery;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.api.in.response.balance.BalanceRawContextResponse;
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
import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceQueryService implements GetBalanceQuery {

  private final GetDriverQuery driverQuery;
  private final GetQWeekQuery qWeekQuery;
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
  public List<BalanceResponse> getAll() {
    return balanceLoadPort.loadAll().stream()
        .map(balanceResponseMapper::toResponse)
        .collect(toList());
  }

  @Override
  public BalanceResponse getById(final Long id) {
    return balanceResponseMapper.toResponse(balanceLoadPort.loadById(id));
  }

  private Map<String, List<TransactionResponse>> getTransactionsMap(
      final Long driverId, final Long qWeekId) {
    return transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId).stream()
        .collect(groupingBy(transactionResponse -> transactionResponse.getKind()));
  }

  private QWeekResponse getStartWeekForRawCalculation(final Balance latestBalance) {
    if (latestBalance == null) {
      return qWeekQuery.getFirstWeek();
    } else {
      return qWeekQuery.getOneAfterById(latestBalance.getQWeekId());
    }
  }

  @Override
  public BalanceRawContextResponse getRawContextByDriverIdAndQWeekId(
      final Long driverId, final Long qWeekId) {
    Balance requestedWeekBalance = null;
    Balance previousWeekBalance = null;
    Map<String, List<TransactionResponse>> transactionsMap = null;

    final var previousWeekId = qWeekQuery.getOneBeforeById(qWeekId).getId();
    requestedWeekBalance =
        balanceLoadPort.loadByDriverIdAndQWeekIdAndDerived(driverId, qWeekId, true);
    if (requestedWeekBalance != null) {
      previousWeekBalance =
          balanceLoadPort.loadByDriverIdAndQWeekIdAndDerived(driverId, previousWeekId, true);

      return BalanceRawContextResponse.builder()
          .requestedWeekBalance(balanceResponseMapper.toResponse(requestedWeekBalance))
          .previousWeekBalance(balanceResponseMapper.toResponse(previousWeekBalance))
          .transactionsByKind(getTransactionsMap(driverId, qWeekId))
          .build();
    }
    var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);
    final var startQWeek = getStartWeekForRawCalculation(latestBalance);
    previousWeekBalance = latestBalance;
    final var weeksForCalculation =
        qWeekQuery.getAllBetweenByIdsDefaultOrder(startQWeek.getId(), qWeekId);
    final var calculator = getDryRunStrategy();
    final var driver = driverQuery.getById(driverId);
    for (int i = 0; i < weeksForCalculation.size(); i++) {
      final var week = weeksForCalculation.get(i);
      final var weekTransactions = getTransactionsMap(driverId, week.getId());
      final var balanceWrapper =
          calculator.calculateBalance(driver, week, previousWeekBalance, weekTransactions);
      requestedWeekBalance = balanceWrapper.getRequestedWeekBalance();
      transactionsMap = balanceWrapper.getTransactionsByKind();

      if (i < weeksForCalculation.size() - 1) {
        previousWeekBalance = balanceWrapper.getRequestedWeekBalance();
      }
    }

    return BalanceRawContextResponse.builder()
        .requestedWeekBalance(balanceResponseMapper.toResponse(requestedWeekBalance))
        .previousWeekBalance(balanceResponseMapper.toResponse(previousWeekBalance))
        .transactionsByKind(transactionsMap)
        .build();
  }

  @Override
  public BalanceResponse getRawCurrentByDriver(final Long driverId) {
    final var currentQWeek = qWeekQuery.getCurrentWeek();

    return getRawContextByDriverIdAndQWeekId(driverId, currentQWeek.getId())
        .getRequestedWeekBalance();
  }

  @Override
  public BalanceResponse getByDriverIdAndQWeekId(final Long driverId, final Long qWeekId) {
    return balanceResponseMapper.toResponse(
        balanceLoadPort.loadByDriverIdAndQWeekIdAndDerived(driverId, qWeekId, true));
  }

/*  @Override
  public BigDecimal getAmountRepairmentByDriver(final Long driverId) {
    final var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);
    final var repairmentBalanceSum =
        latestBalance != null ? latestBalance.getRepairmentAmount() : ZERO;
    final var repairmentTransactionKinds = transactionKindQuery.getAllRepairment();
    final var repairmentTransactionKindIds =
        repairmentTransactionKinds.stream().map(TransactionKindResponse::getId).toList();

    final var rawSum = getRawTransactionsSum(latestBalance, driverId, repairmentTransactionKindIds);

    return round(repairmentBalanceSum.add(rawSum));
  }*/

/*  @Override
  public BigDecimal getAmountRepairmentByDriverWithQKasko(final Long driverId) {
    final var totalRepairment = getAmountRepairmentByDriver(driverId).abs();
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
  }*/

  @Override
  public BigDecimal getRawBalanceTotalByDriverIdAndQWeekId(
      final Long driverId, final Long qWeekId) {
    final var balance = balanceLoadPort.loadByDriverIdAndQWeekIdAndDerived(driverId, qWeekId, true);
    if (balance != null) {

      return qRound(balance.getAmountsSumWithoutRepairment());
    }
    final var qWeek = qWeekQuery.getById(qWeekId);
    final var endDate = qWeek.getEnd();
    final var filterBuilder =
        PeriodAndKindAndDriverTransactionFilter.builder().dateEnd(endDate).driverId(driverId);
    final var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);
    if (latestBalance == null) {
      final var startDate = getFirstDayOfYear(qWeekQuery.getFirstWeek().getYear());
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

    return qRound(fromBalance.add(rawTransactionSum));
  }

  @Override
  public Long getCountByDriver(final Long driverId) {
    return balanceLoadPort.loadCountByDriver(driverId);
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
        getLastDayOfWeekInYear(latestBalanceYear, latestBalanceWeekNumber);

    return latestCalculatedDate.plusDays(1L);
  }

  @Override
  public BalanceResponse getLatest() {
    final var latestBalance = balanceLoadPort.loadLatest();

    return balanceResponseMapper.toResponse(latestBalance);
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

    return qRound(result);
  }

  private List<Long> getNonRepairmentTransactionKindIds() {
    final var nonRepairmentTransactionKinds = transactionKindQuery.getAllNonRepairment();
    return nonRepairmentTransactionKinds.stream().map(TransactionKindResponse::getId).toList();
  }
}
