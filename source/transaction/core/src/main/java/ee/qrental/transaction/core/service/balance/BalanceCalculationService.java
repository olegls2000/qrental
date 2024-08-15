package ee.qrental.transaction.core.service.balance;

import static ee.qrental.common.utils.QTimeUtils.getWeekNumber;
import static ee.qrental.transaction.core.service.balance.calculator.BalanceCalculatorStrategy.SAVING;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.*;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.request.balance.BalanceCalculationAddRequest;
import ee.qrental.transaction.api.in.usecase.balance.BalanceCalculationAddUseCase;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.api.out.balance.BalanceCalculationAddPort;
import ee.qrental.transaction.api.out.balance.BalanceCalculationLoadPort;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.core.mapper.balance.BalanceCalculationAddRequestMapper;
import ee.qrental.transaction.core.service.balance.calculator.BalanceCalculatorStrategy;
import ee.qrental.transaction.domain.Transaction;
import ee.qrental.transaction.domain.balance.Balance;
import ee.qrental.transaction.domain.balance.BalanceCalculation;
import ee.qrental.transaction.domain.balance.BalanceCalculationResult;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationService implements BalanceCalculationAddUseCase {
  private static final LocalDate DEFAULT_START_DATE = LocalDate.of(2023, Month.JANUARY, 2);

  private final GetTransactionQuery transactionQuery;
  private final GetQWeekQuery qWeekQuery;
  private final GetDriverQuery driverQuery;
  private final BalanceCalculationLoadPort balanceCalculationLoadPort;
  private final TransactionLoadPort transactionLoadPort;
  private final BalanceCalculationAddPort balanceCalculationAddPort;
  private final BalanceLoadPort balanceLoadPort;
  private final BalanceCalculationAddRequestMapper addRequestMapper;
  private final List<BalanceCalculatorStrategy> calculatorStrategies;

  private BalanceCalculatorStrategy getSavingStrategy() {
    return calculatorStrategies.stream()
        .filter(strategy -> strategy.canApply(SAVING))
        .findFirst()
        .get();
  }

  @Transactional
  @Override
  public void add(final BalanceCalculationAddRequest addRequest) {
    final var calculationStartTime = System.currentTimeMillis();
    final var requestedQWeekId = addRequest.getQWeekId();
    final var domain = addRequestMapper.toDomain(addRequest);
    final var requestedQWeek = qWeekQuery.getById(requestedQWeekId);

    var latestCalculatedWeek = getLatestCalculatedWeek();
    setStartAndEndDates(domain, latestCalculatedWeek, requestedQWeek);
    final var latestCalculatedWeekId =
        latestCalculatedWeek == null ? null : latestCalculatedWeek.getId();
    final var nextAfterCalculatedQWeek = qWeekQuery.getOneAfterById(latestCalculatedWeekId);
    final var nextAfterCalculatedQWeekId =
        nextAfterCalculatedQWeek == null ? null : nextAfterCalculatedQWeek.getId();
    final var qWeeksForCalculation =
        qWeekQuery.getQWeeksFromPeriodOrdered(
            nextAfterCalculatedQWeekId, requestedQWeekId, GetQWeekQuery.DEFAULT_COMPARATOR);
    final var calculatorSavingStrategy = getSavingStrategy();
    final var drivers = driverQuery.getAll();
    qWeeksForCalculation.forEach(
        week ->
            drivers.forEach(
                driver -> {
                  final var previousQWeek = qWeekQuery.getOneBeforeById(week.getId());
                  final var driverId = driver.getId();
                  final var previousQWeekBalance = getPreviousWeekBalance(driverId, previousQWeek);
                  final var weekTransactions =
                      transactionQuery.getAllByDriverIdAndQWeekId(driverId, week.getId()).stream()
                          .collect(
                              groupingBy(transactionResponse -> transactionResponse.getKind()));
                  final var balanceWrapper =
                      calculatorSavingStrategy.calculateBalance(
                          driver, week, previousQWeekBalance, weekTransactions);
                  final var balanceCalculationResult =
                      getBalanceCalculationResult(balanceWrapper.getRequestedWeekBalance(), week);
                  domain.getResults().add(balanceCalculationResult);
                }));
    balanceCalculationAddPort.add(domain);
    final var calculationEndTime = System.currentTimeMillis();
    final var calculationDuration = calculationEndTime - calculationStartTime;
    System.out.printf(
        "----> Time: Balance Calculation took %d milli seconds \n", calculationDuration);
  }

  private Balance getPreviousWeekBalance(final Long driverId, final QWeekResponse previousWeek) {
    final var zeroBalance =
        Balance.builder()
            .qWeekId(null)
            .positiveAmount(ZERO)
            .feeAmount(ZERO)
            .feeAbleAmount(ZERO)
            .nonFeeAbleAmount(ZERO)
            .repairmentAmount(ZERO)
            .driverId(driverId)
            .derived(TRUE)
            .build();

    if (previousWeek == null) {
      return zeroBalance;
    }

    final var balancesCount = balanceLoadPort.loadCountByDriver(driverId);
    if (balancesCount == 0) {
      return zeroBalance;
    }

    final var previousWeekBalance =
        balanceLoadPort.loadByDriverIdAndQWeekIdAndDerived(driverId, previousWeek.getId(), true);

    if (previousWeekBalance == null) {
      throw new RuntimeException(
          format(
              "Derived Balance for previous qWeek %d and driver with id %d, must exist",
              previousWeek.getNumber(), driverId));
    }

    return previousWeekBalance;
  }

  private BalanceCalculationResult getBalanceCalculationResult(
      final Balance savedBalance, final QWeekResponse week) {
    final var transactionIds =
        transactionLoadPort
            .loadAllByDriverIdAndBetweenDays(
                savedBalance.getDriverId(), week.getStart(), week.getEnd())
            .stream()
            .map(Transaction::getId)
            .collect(toSet());
    return BalanceCalculationResult.builder()
        .balance(savedBalance)
        .transactionIds(transactionIds)
        .build();
  }

  private QWeekResponse getLatestCalculatedWeek() {
    var lastCalculationDate = balanceCalculationLoadPort.loadLastCalculatedDate();
    if (lastCalculationDate == null) {

      return null;
    }
    final var year = lastCalculationDate.getYear();
    final var weekNumber = getWeekNumber(lastCalculationDate);

    return qWeekQuery.getByYearAndNumber(year, weekNumber);
  }

  private void setStartAndEndDates(
      final BalanceCalculation domain,
      QWeekResponse latestCalculatedWeek,
      QWeekResponse latestRequestedWeek) {
    final var startDate =
        latestCalculatedWeek == null
            ? DEFAULT_START_DATE
            : latestCalculatedWeek.getEnd().plusDays(1);
    final var endDate = latestRequestedWeek.getEnd();

    domain.setStartDate(startDate);
    domain.setEndDate(endDate);
  }
}
