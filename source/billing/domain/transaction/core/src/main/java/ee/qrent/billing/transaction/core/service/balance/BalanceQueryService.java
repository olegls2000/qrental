package ee.qrent.billing.transaction.core.service.balance;

import static ee.qrent.billing.transaction.core.service.balance.calculator.BalanceCalculatorStrategy.DRY_RUN;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrent.billing.transaction.api.in.query.filter.PeriodAndKindAndDriverTransactionFilter;
import ee.qrent.billing.transaction.api.in.query.kind.GetTransactionKindQuery;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.transaction.api.in.response.balance.BalanceRawContextResponse;
import ee.qrent.billing.transaction.api.in.response.balance.BalanceResponse;
import ee.qrent.billing.transaction.api.in.response.kind.TransactionKindResponse;
import ee.qrent.billing.transaction.api.out.balance.BalanceLoadPort;
import ee.qrent.billing.transaction.core.mapper.balance.BalanceResponseMapper;
import ee.qrent.billing.transaction.core.service.balance.calculator.BalanceCalculatorStrategy;
import ee.qrent.billing.transaction.core.service.balance.calculator.BalanceRawContext;
import ee.qrent.billing.transaction.domain.balance.Balance;

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

  private QWeekResponse getStartWeekForRawCalculation(final Balance latestBalance) {
    if (latestBalance == null) {
      return qWeekQuery.getFirstWeek();
    } else {
      return qWeekQuery.getOneAfterById(latestBalance.getQWeekId());
    }
  }

  private Balance getDefault(final Long qWeekId, final Long driverId) {
    final var requestedBalance = balanceLoadPort.loadByDriverIdAndQWeekIdAndDerived(driverId, qWeekId, true);


    return requestedBalance != null ? requestedBalance : Balance.builder()
        .qWeekId(qWeekId)
        .feeAbleAmount(BigDecimal.ZERO)
        .feeAmount(BigDecimal.ZERO)
        .nonFeeAbleAmount(BigDecimal.ZERO)
        .positiveAmount(BigDecimal.ZERO)
        .repairmentAmount(BigDecimal.ZERO)
        .derived(Boolean.TRUE)
        .driverId(driverId)
        .build();
  }

  @Override
  public BalanceRawContextResponse getRawContextByDriverIdAndQWeekId(
      final Long driverId, final Long qWeekId) {

    return mapToResponse(getRawContext(driverId, qWeekId));
  }

  private BalanceRawContext getRawContext(final Long driverId, final Long requestedQWeekId) {
    var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);
    final var requestedWeek = qWeekQuery.getById(requestedQWeekId);
    final var previousWeek = qWeekQuery.getOneBeforeById(requestedQWeekId);
    final var previousWeekId = previousWeek.getId();
    Balance requestedWeekBalance;
    Balance previousWeekBalance;
    if (latestBalance != null) {
      final var latestQWeekId = latestBalance.getQWeekId();
      final var latestQWeek = qWeekQuery.getById(latestQWeekId);
      if (latestQWeek.compareTo(requestedWeek) > 0) {
        requestedWeekBalance = getDefault(requestedQWeekId, driverId);
        previousWeekBalance = getDefault(previousWeekId, driverId);

        return BalanceRawContext.builder()
            .requestedWeekBalance(requestedWeekBalance)
            .previousWeekBalance(previousWeekBalance)
            .transactionsByKind(getTransactionsMap(driverId, requestedQWeekId))
            .build();
      }
    }

    requestedWeekBalance =
        balanceLoadPort.loadByDriverIdAndQWeekIdAndDerived(driverId, requestedQWeekId, true);
    previousWeekBalance =
        balanceLoadPort.loadByDriverIdAndQWeekIdAndDerived(driverId, previousWeekId, true);
    if (requestedWeekBalance != null && previousWeekBalance != null) {

      return BalanceRawContext.builder()
          .requestedWeekBalance(requestedWeekBalance)
          .previousWeekBalance(previousWeekBalance)
          .transactionsByKind(getTransactionsMap(driverId, requestedQWeekId))
          .build();
    }
    return getRawBalanceContextForUncalculatedPeriod(driverId, requestedQWeekId, latestBalance);
  }

  private List<QWeekResponse> getListOfQWeeksForRawCalculation(
      final Balance latestBalance, final Long requestedWeekId) {
    final var startQWeek = getStartWeekForRawCalculation(latestBalance);

    return qWeekQuery.getAllBetweenByIdsDefaultOrder(startQWeek.getId(), requestedWeekId);
  }

  private BalanceRawContext getRawBalanceContextForUncalculatedPeriod(
      final Long driverId, final Long requestedQWeekId, final Balance latestCalculatedBalance) {
    Balance previousWeekBalance = latestCalculatedBalance;
    Balance requestedWeekBalance = null;
    final var weeksForCalculation =
        getListOfQWeeksForRawCalculation(latestCalculatedBalance, requestedQWeekId);
    final var calculator = getDryRunStrategy();
    final var driver = driverQuery.getById(driverId);
    Map<String, List<TransactionResponse>> weekTransactions = null;
    for (int i = 0; i < weeksForCalculation.size(); i++) {
      final var week = weeksForCalculation.get(i);
      weekTransactions = getTransactionsMap(driverId, week.getId());
      final var balanceRawContext =
          calculator.calculateBalance(driver, week, previousWeekBalance, weekTransactions);
      requestedWeekBalance = balanceRawContext.getRequestedWeekBalance();
      if (i < weeksForCalculation.size() - 1) {
        previousWeekBalance = balanceRawContext.getRequestedWeekBalance();
      }
    }

    return BalanceRawContext.builder()
        .previousWeekBalance(previousWeekBalance)
        .requestedWeekBalance(requestedWeekBalance)
        .transactionsByKind(weekTransactions)
        .build();
  }

  @Override
  public BalanceResponse getRawCurrentByDriver(final Long driverId) {
    final var currentQWeek = qWeekQuery.getCurrentWeek();

    return getRawContextByDriverIdAndQWeekId(driverId, currentQWeek.getId())
        .getRequestedWeekBalance();
  }

  @Override
  public BalanceResponse getRawByDriverAndDate(final Long driverId, final LocalDate date) {
    final var driver = driverQuery.getById(driverId);

    final var requestedWeek = qWeekQuery.getByDate(date);
    final var previousWeek = qWeekQuery.getOneBeforeById(requestedWeek.getId());

    final var previousWeekRawContext = getRawContext(driverId, previousWeek.getId());

    final var monday = requestedWeek.getStart();

    final var transactionKindIds =
        transactionKindQuery.getAll().stream().map(TransactionKindResponse::getId).toList();

    final var transactionFilter =
        PeriodAndKindAndDriverTransactionFilter.builder()
            .driverId(driverId)
            .dateStart(monday)
            .dateEnd(date)
            .transactionKindIds(transactionKindIds)
            .build();

    final var transactionMapByKind =
        transactionQuery.getAllByFilter(transactionFilter).stream()
            .collect(groupingBy(TransactionResponse::getKind));

      final var calculator = getDryRunStrategy();
    final var balanceRawContext =
        calculator.calculateBalance(
            driver,
            previousWeek,
            previousWeekRawContext.getRequestedWeekBalance(),
            transactionMapByKind);

    return balanceResponseMapper.toResponse(balanceRawContext.getRequestedWeekBalance());
  }

  @Override
  public BalanceResponse getByDriverIdAndQWeekId(final Long driverId, final Long qWeekId) {
    return balanceResponseMapper.toResponse(
        balanceLoadPort.loadByDriverIdAndQWeekIdAndDerived(driverId, qWeekId, true));
  }

  @Override
  public Long getCountByDriver(final Long driverId) {
    return balanceLoadPort.loadCountByDriver(driverId);
  }

  @Override
  public BalanceResponse getLatest() {
    final var latestBalance = balanceLoadPort.loadLatest();

    return balanceResponseMapper.toResponse(latestBalance);
  }

  private Map<String, List<TransactionResponse>> getTransactionsMap(
      final Long driverId, final Long qWeekId) {
    return transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId).stream()
        .collect(groupingBy(TransactionResponse::getKind));
  }

  private BalanceRawContextResponse mapToResponse(final BalanceRawContext balanceRawContext) {

    return BalanceRawContextResponse.builder()
        .requestedWeekBalance(
            balanceResponseMapper.toResponse(balanceRawContext.getRequestedWeekBalance()))
        .previousWeekBalance(
            balanceResponseMapper.toResponse(balanceRawContext.getPreviousWeekBalance()))
        .transactionsByKind(balanceRawContext.getTransactionsByKind())
        .build();
  }
}
