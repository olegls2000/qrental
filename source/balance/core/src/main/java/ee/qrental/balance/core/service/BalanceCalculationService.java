package ee.qrental.balance.core.service;

import static ee.qrental.balance.core.service.FeeTransactionCreator.TRANSACTION_TYPE_NAME_FEE_DEBT;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.*;

import ee.qrental.balance.api.in.request.BalanceCalculationAddRequest;
import ee.qrental.balance.api.in.usecase.BalanceCalculationAddUseCase;
import ee.qrental.balance.api.out.BalanceAddPort;
import ee.qrental.balance.api.out.BalanceCalculationAddPort;
import ee.qrental.balance.core.mapper.BalanceCalculationAddRequestMapper;
import ee.qrental.balance.domain.Balance;
import ee.qrental.balance.domain.BalanceCalculationResult;
import ee.qrental.common.core.utils.Week;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodFilter;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import jakarta.transaction.Transactional;
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

  @Transactional
  @Override
  public void add(final BalanceCalculationAddRequest addRequest) {
    final var domain = addRequestMapper.toDomain(addRequest);
    final var actionDate = addRequest.getActionDate();
    final var weekIterator = balanceCalculationPeriodService.getWeekIterator(actionDate);

    final var drivers = driverQuery.getAll();
    while (weekIterator.hasNext()) {
      final var week = weekIterator.next();
      final var weekTransactions = getNotFeeTransactionsMapForWeek(week);
      drivers.stream()
          .map(DriverResponse::getId)
          .forEach(
              driverId -> {
                feeTransactionCreator.creteFeeTransactionIfNecessary(week, driverId);
                final var driversTransactions =
                    weekTransactions.getOrDefault(driverId, emptyList());
                final var balance = getBalance(week, driverId, driversTransactions);
                final var savedBalance = balanceAddPort.add(balance);
                final var balanceCalculationResult =
                    getBalanceCalculationResult(driversTransactions, savedBalance);
                domain.getResults().add(balanceCalculationResult);
              });
    }
    balanceCalculationAddPort.add(domain);
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
      final Week week, final Long driverId, final List<TransactionResponse> driversTransactions) {
    final var amount = amountCalculator.calculate(week, driverId, driversTransactions);
    final var feeBalance = feeCalculator.calculate(week, driverId);

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
