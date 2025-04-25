package ee.qrent.billing.transaction.core.service.balance;

import static ee.qrent.billing.transaction.core.service.balance.calculator.BalanceCalculatorStrategy.DRY_RUN;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.transaction.api.in.response.balance.BalanceRawContextResponse;
import ee.qrent.billing.transaction.api.in.response.balance.BalanceResponse;
import ee.qrent.billing.transaction.api.out.balance.BalanceLoadPort;
import ee.qrent.billing.transaction.core.mapper.balance.BalanceResponseMapper;
import ee.qrent.billing.transaction.core.service.balance.calculator.BalanceCalculatorStrategy;
import ee.qrent.billing.transaction.domain.balance.Balance;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceQueryService implements GetBalanceQuery {

  private final GetDriverQuery driverQuery;
  private final GetQWeekQuery qWeekQuery;
  private final GetTransactionQuery transactionQuery;
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

  @Override
  public BalanceRawContextResponse getRawContextByDriverIdAndQWeekId(
      final Long driverId, final Long qWeekId) {
    Balance requestedWeekBalance;
    Balance previousWeekBalance;
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
