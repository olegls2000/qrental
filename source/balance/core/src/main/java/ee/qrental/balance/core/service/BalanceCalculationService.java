package ee.qrental.balance.core.service;

import static ee.qrental.transaction.api.in.TransactionConstants.TRANSACTION_TYPE_NAME_FEE_DEBT;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.*;

import ee.qrental.balance.api.in.request.BalanceCalculationAddRequest;
import ee.qrental.balance.api.in.usecase.BalanceCalculationAddUseCase;
import ee.qrental.balance.api.out.BalanceAddPort;
import ee.qrental.balance.api.out.BalanceCalculationAddPort;
import ee.qrental.balance.api.out.BalanceLoadPort;
import ee.qrental.balance.core.mapper.BalanceCalculationAddRequestMapper;
import ee.qrental.balance.domain.Balance;
import ee.qrental.balance.domain.BalanceCalculationResult;
import ee.qrental.common.core.utils.Week;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodFilter;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationService implements BalanceCalculationAddUseCase {

  private final BalanceCalculationPeriodService balanceCalculationPeriodService;
  private final BalanceCalculationAddRequestMapper addRequestMapper;
  private final BalanceCalculationAddPort balanceCalculationAddPort;
  private final BalanceAddPort balanceAddPort;
  private final GetTransactionQuery transactionQuery;
  private final AmountCalculator amountCalculator;
  private final FeeCalculator feeCalculator;
  private final FeeTransactionCreator feeTransactionCreator;
  private final GetDriverQuery driverQuery;
  private final BalanceLoadPort loadPort;

  @Transactional
  @Override
  public void add(final BalanceCalculationAddRequest addRequest) {
    final var calculationStartTime = System.currentTimeMillis();
    final var domain = addRequestMapper.toDomain(addRequest);
    final var actionDate = addRequest.getActionDate();
    final var weekIterator = balanceCalculationPeriodService.getWeekIterator(actionDate);

    final var drivers = driverQuery.getAll();
    while (weekIterator.hasNext()) {
      final var week = weekIterator.next();
      final var weekTransactions = getNotFeeTransactionsMapForWeek(week);
      drivers.stream()
          .forEach(
              driver -> {
                final var feeTransactionSum =
                    feeTransactionCreator.creteFeeTransactionIfNecessary(week, driver);
                final var driverId = driver.getId();
                final var driversTransactions =
                    weekTransactions.getOrDefault(driverId, emptyList());
                final var balance =
                    getBalance(week, driverId, driversTransactions, feeTransactionSum);
                final var savedBalance = balanceAddPort.add(balance);
                final var balanceCalculationResult =
                    getBalanceCalculationResult(driversTransactions, savedBalance);
                domain.getResults().add(balanceCalculationResult);
                System.out.printf(
                    "Balance for Driver with id: %d and week %d was calculated",
                    driverId, week.weekNumber());
              });
    }
    balanceCalculationAddPort.add(domain);
    final var calculationEndTime = System.currentTimeMillis();
    final var calculationDuration = calculationEndTime - calculationStartTime;
    System.out.printf("Calculation took %d milli seconds", calculationDuration);
  }

  private BalanceCalculationResult getBalanceCalculationResult(
      List<TransactionResponse> driversTransactions, Balance savedBalance) {
    final var transactionIds =
        driversTransactions.stream().map(TransactionResponse::getId).collect(toSet());
    final var result =
        BalanceCalculationResult.builder()
            .balance(savedBalance)
            .transactionIds(transactionIds)
            .build();
    return result;
  }

  private Map<Long, List<TransactionResponse>> getNotFeeTransactionsMapForWeek(final Week week) {
    final var filter = PeriodFilter.builder().dateStart(week.start()).datEnd(week.end()).build();
    return transactionQuery.getAllByFilter(filter).stream()
        .filter(tx -> !tx.getType().equals(TRANSACTION_TYPE_NAME_FEE_DEBT))
        .collect(groupingBy(TransactionResponse::getDriverId));
  }

  private Balance getBalance(
      final Week week,
      final Long driverId,
      final List<TransactionResponse> driversTransactions,
      final BigDecimal feeTransactionSum) {
    final var previousWeekNumber = week.weekNumber() - 1;
    final var previousWeekBalance =
        loadPort.loadByDriverIdAndYearAndWeekNumber(driverId, week.getYear(), previousWeekNumber);

    final var amount = amountCalculator.calculate(driversTransactions, previousWeekBalance);
    final var feeBalance = feeCalculator.calculate(feeTransactionSum, previousWeekBalance);

    return Balance.builder()
        .driverId(driverId)
        .weekNumber(week.weekNumber())
        .year(week.getYear())
        .created(LocalDate.now())
        .amount(amount)
        .fee(feeBalance)
        .build();
  }
}
