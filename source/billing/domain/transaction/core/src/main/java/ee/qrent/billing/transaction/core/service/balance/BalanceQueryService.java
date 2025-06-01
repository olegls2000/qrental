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
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.transaction.api.in.response.balance.BalanceRawContextResponse;
import ee.qrent.billing.transaction.api.in.response.balance.BalanceResponse;
import ee.qrent.billing.transaction.api.in.response.kind.TransactionKindResponse;
import ee.qrent.billing.transaction.api.out.balance.BalanceLoadPort;
import ee.qrent.billing.transaction.core.mapper.balance.BalanceResponseMapper;
import ee.qrent.billing.transaction.core.service.balance.calculator.BalanceCalculatorStrategy;
import ee.qrent.billing.transaction.domain.balance.Balance;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
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

  private Balance getDefault(final Long qWeekId, final Long driverId) {

    return Balance.builder()
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
    var latestBalance = balanceLoadPort.loadLatestByDriver(driverId);
    final var requestedWeek = qWeekQuery.getById(qWeekId);
    final var previousWeek = qWeekQuery.getOneBeforeById(qWeekId);
    final var previousWeekId = previousWeek.getId();
    Balance requestedWeekBalance;
    Balance previousWeekBalance;

    if (latestBalance != null) {
      final var latestQWeekId = latestBalance.getQWeekId();
      final var latestQWeek = qWeekQuery.getById(latestQWeekId);

      if (latestQWeek.compareTo(requestedWeek) > 0) {
        requestedWeekBalance = getDefault(qWeekId, driverId);
        previousWeekBalance = getDefault(previousWeekId, driverId);

        return assembleRawContext(
            requestedWeekBalance, previousWeekBalance, getTransactionsMap(driverId, qWeekId));
      }
    }
    requestedWeekBalance =
        balanceLoadPort.loadByDriverIdAndQWeekIdAndDerived(driverId, qWeekId, true);

    previousWeekBalance =
        balanceLoadPort.loadByDriverIdAndQWeekIdAndDerived(driverId, previousWeekId, true);

    if (requestedWeekBalance != null && previousWeekBalance != null) {

      return assembleRawContext(
          requestedWeekBalance, previousWeekBalance, getTransactionsMap(driverId, qWeekId));
    }

    final var startQWeek = getStartWeekForRawCalculation(latestBalance);
    previousWeekBalance = latestBalance;
    final var weeksForCalculation =
        qWeekQuery.getAllBetweenByIdsDefaultOrder(startQWeek.getId(), qWeekId);
    final var calculator = getDryRunStrategy();
    final var driver = driverQuery.getById(driverId);
    Map<String, List<TransactionResponse>> transactionsMap = null;
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

    return assembleRawContext(requestedWeekBalance, previousWeekBalance, transactionsMap);
  }

  private BalanceRawContextResponse assembleRawContext(
      final Balance requestedWeekBalance,
      final Balance previousWeekBalance,
      final Map<String, List<TransactionResponse>> transactionsByKind) {

    return BalanceRawContextResponse.builder()
        .requestedWeekBalance(balanceResponseMapper.toResponse(requestedWeekBalance))
        .previousWeekBalance(balanceResponseMapper.toResponse(previousWeekBalance))
        .transactionsByKind(transactionsByKind)
        .build();
  }

  @Override
  public BalanceResponse getRawCurrentByDriver(final Long driverId) {
    final var currentQWeek = qWeekQuery.getCurrentWeek();

    return getRawContextByDriverIdAndQWeekId(driverId, currentQWeek.getId())
        .getRequestedWeekBalance();
  }

  @Override
  public BalanceResponse getRawByDriverAndWednesday(
      final Long driverId, final LocalDate wednesday) {
    if (wednesday.getDayOfWeek() != DayOfWeek.WEDNESDAY) {
      throw new RuntimeException("Method call allowed only with Wednesday day");
    }

    final var requestedWeek = qWeekQuery.getByDate(wednesday);
    final var monday = requestedWeek.getStart();
    final var previousWeek = qWeekQuery.getOneBeforeById(requestedWeek.getId());

    final var transactionKindIds =
        transactionKindQuery.getAllByCodes(Arrays.asList("P")).stream()
            .map(TransactionKindResponse::getId)
            .toList();

    final var transactionFilter =
        PeriodAndKindAndDriverTransactionFilter.builder()
            .driverId(driverId)
            .dateStart(monday)
            .dateEnd(wednesday)
            .transactionKindIds(transactionKindIds)
            .build();

    final var transactions = transactionQuery.getAllByFilter(transactionFilter);



    return null;
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
}
