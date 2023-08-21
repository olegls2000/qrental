package ee.qrental.transaction.core.service.balance;

import static ee.qrental.transaction.api.in.TransactionConstants.*;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

import ee.qrental.common.core.utils.Week;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodAndDriverFilter;
import ee.qrental.transaction.api.in.request.balance.BalanceCalculationAddRequest;
import ee.qrental.transaction.api.in.response.TransactionResponse;
//import jakarta.transaction.Transactional;
import ee.qrental.transaction.api.in.usecase.balance.BalanceCalculationAddUseCase;
import ee.qrental.transaction.api.out.balance.BalanceAddPort;
import ee.qrental.transaction.api.out.balance.BalanceCalculationAddPort;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.core.mapper.balance.BalanceCalculationAddRequestMapper;
import ee.qrental.transaction.domain.balance.Balance;
import ee.qrental.transaction.domain.balance.BalanceCalculationResult;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationService implements BalanceCalculationAddUseCase {

  private final BalanceCalculationPeriodService balanceCalculationPeriodService;
  private final BalanceCalculationAddRequestMapper addRequestMapper;
  private final BalanceCalculationAddPort balanceCalculationAddPort;
  private final BalanceAddPort balanceAddPort;
  private final GetTransactionQuery transactionQuery;
  private final BalanceAmountCalculator amountCalculator;
  private final BalanceFeeCalculator feeCalculator;
  private final FeeTransactionCreator feeTransactionCreator;
  private final FeeDebtReplenishService feeDebtReplenishService;
  private final GetDriverQuery driverQuery;
  private final BalanceLoadPort loadPort;

  //@Transactional
  @Override
  public void add(final BalanceCalculationAddRequest addRequest) {
    final var calculationStartTime = System.currentTimeMillis();
    final var lastYear = addRequest.getLastYear();
    final var lastWeek = addRequest.getLastWeek();
    final var weekIterator = balanceCalculationPeriodService.getWeekIterator(lastYear, lastWeek);
    final var domain = addRequestMapper.toDomain(addRequest);
    domain.setStartDate(weekIterator.getStartPeriod());
    domain.setEndDate(weekIterator.getEndPeriod());

    final var drivers = driverQuery.getAll();
    while (weekIterator.hasNext()) {
      final var week = weekIterator.next();
      drivers.stream()
          .forEach(
              driver -> {
                final var feeTransactionOpt =
                    feeTransactionCreator.creteFeeTransactionIfNecessary(week, driver);
                final var driverId = driver.getId();
                final var driversTransactions = getListOfNonFeeTransactions(week, driverId);
                final var feeReplenishTransactions = feeDebtReplenishService.replenish(week, driver);
                final var balance =
                    getBalance(week, driverId, driversTransactions, feeTransactionOpt, feeReplenishTransactions);
                final var savedBalance = balanceAddPort.add(balance);
                final var balanceCalculationResult =
                    getBalanceCalculationResult(
                        driversTransactions,
                            savedBalance,
                            feeTransactionOpt,
                            feeReplenishTransactions,
                            Collections.emptyList());
                domain.getResults().add(balanceCalculationResult);
                System.out.printf(
                    "Balance for Driver with id: %d and week %d was calculated. ",
                    driverId, week.weekNumber());
              });
    }
    balanceCalculationAddPort.add(domain);
    final var calculationEndTime = System.currentTimeMillis();
    final var calculationDuration = calculationEndTime - calculationStartTime;
    System.out.printf("Balance Calculation took %d milli seconds", calculationDuration);
  }

  private BalanceCalculationResult getBalanceCalculationResult(
     final List<TransactionResponse> driversTransactions,
     final  Balance savedBalance,
     final Optional<TransactionResponse> feeTransactionOpt,
     final List<TransactionResponse> feeReplenishTransactions,
     final List<TransactionResponse> compensationTransactions
      ) {
    final var transactionIds =
        driversTransactions.stream().map(TransactionResponse::getId).collect(toSet());
    if (feeTransactionOpt.isPresent()) {
      transactionIds.add(feeTransactionOpt.get().getId());
    }
    final var result =
        BalanceCalculationResult.builder()
            .balance(savedBalance)
            .transactionIds(transactionIds)
            .build();
    return result;
  }

  private List<TransactionResponse> getListOfNonFeeTransactions(final Week week, final Long driverId){
    final var filter = PeriodAndDriverFilter.builder()
            .dateStart(week.start())
            .dateEnd(week.end())
            .driverId(driverId)
            .build();

    return transactionQuery.getAllByFilter(filter)
            .stream()
            .filter(tx -> isNotFeeType(tx.getType()))
            .sorted(comparing(TransactionResponse::getRealAmount))
            .toList();
  }

  private Balance getBalance(
      final Week week,
      final Long driverId,
      final List<TransactionResponse> driversTransactions,
      final Optional<TransactionResponse> feeTransactionOpt,
      final List<TransactionResponse> feeReplenishTransactions) {
    final var previousWeekNumber = week.weekNumber() - 1;
    final var previousWeekBalance =
        loadPort.loadByDriverIdAndYearAndWeekNumberOrDefault(driverId, week.getYear(), previousWeekNumber);

    final var amount = amountCalculator.calculate(driversTransactions, previousWeekBalance);
    final var feeTransactionSum =
        feeTransactionOpt
            .orElse(TransactionResponse.builder().id(null).realAmount(BigDecimal.ZERO).build())
            .getRealAmount();

    final var replenishFeeSum =
        feeReplenishTransactions.stream()
            .map(TransactionResponse::getRealAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    final var feeWeekTotal = feeTransactionSum.subtract(replenishFeeSum);

    final var feeBalance = feeCalculator.calculate(feeWeekTotal, previousWeekBalance);

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
