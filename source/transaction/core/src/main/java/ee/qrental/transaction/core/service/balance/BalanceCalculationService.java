package ee.qrental.transaction.core.service.balance;

import static ee.qrental.common.core.utils.QTimeUtils.getLastDayOfWeekInYear;
import static ee.qrental.transaction.api.in.TransactionConstants.isFeeType;
import static ee.qrental.transaction.api.in.TransactionConstants.isNotFeeType;
import static java.math.BigDecimal.ZERO;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.*;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodAndDriverFilter;
import ee.qrental.transaction.api.in.request.balance.BalanceCalculationAddRequest;
import ee.qrental.transaction.api.in.response.TransactionResponse;
//import jakarta.transaction.Transactional;
import ee.qrental.transaction.api.in.usecase.balance.BalanceCalculationAddUseCase;
import ee.qrental.transaction.api.out.balance.BalanceAddPort;
import ee.qrental.transaction.api.out.balance.BalanceCalculationAddPort;
import ee.qrental.transaction.api.out.balance.BalanceCalculationLoadPort;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.core.mapper.balance.BalanceCalculationAddRequestMapper;
import ee.qrental.transaction.domain.balance.Balance;
import ee.qrental.transaction.domain.balance.BalanceCalculationResult;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationService implements BalanceCalculationAddUseCase {

  private final GetQWeekQuery qWeekQuery;
  private final GetDriverQuery driverQuery;
  private final GetTransactionQuery transactionQuery;
  private final FeeCalculationService feeCalculationService;
  private final FeeReplenishService feeReplenishService;
  private final BalanceCalculationAddPort balanceCalculationAddPort;
  private final BalanceAddPort balanceAddPort;
  private final BalanceLoadPort balanceLoadPort;
  private final BalanceCalculationLoadPort balanceCalculationLoadPort;
  private final BalanceCalculationAddRequestMapper addRequestMapper;

  //@Transactional
  @Override
  public void add(final BalanceCalculationAddRequest addRequest) {
    final var calculationStartTime = System.currentTimeMillis();

    final var calculationQWeekId = addRequest.getQWeekId();
    //final var weekIterator = balanceCalculationPeriodService.getWeekIterator(lastYear, lastWeek);
    final var domain = addRequestMapper.toDomain(addRequest);
    final var qWeek = qWeekQuery.getById(calculationQWeekId);

    final var startDate = balanceCalculationLoadPort.loadLastCalculationDate().plus(1, DAYS);
    //final var endDate = getEndCalculationDate(lastYear, lastWeek);
    final var endDate = getLastDayOfWeekInYear(qWeek.getYear(), qWeek.getNumber());

    domain.setStartDate(startDate);
    domain.setEndDate(endDate);

    final var drivers = driverQuery.getAll();

    qWeekQuery.getAllBeforeById(calculationQWeekId).stream().forEach(week -> driverQuery.getAll().stream()
                    .forEach(
                            driver -> {
                              final var driverId = driver.getId();
                              feeReplenishService.replenish(week, driverId);
                              feeCalculationService.calculate(week, driver);

                              final var driversTransactions = getAllTransactionsByDriverAndWeek(week, driverId);
                              final var balanceToSave = getBalance(week, driverId, driversTransactions);
                              final var balance = balanceAddPort.add(balanceToSave);
                              final var balanceCalculationResult = getBalanceCalculationResult(driversTransactions, balance);
                              domain.getResults().add(balanceCalculationResult);
                              System.out.printf(
                                      "Balance for Driver with id: %d and week %d was calculated.\n",
                                      driverId, week.getNumber());
                            }));

   /* while (weekIterator.hasNext()) {
      final var week = weekIterator.next();
      drivers.stream()
          .forEach(
              driver -> {
                final var driverId = driver.getId();
                feeReplenishService.replenish(week, driverId);
                feeCalculationService.calculate(week, driver);

                final var driversTransactions = getAllTransactionsByDriverAndWeek(week, driverId);
                final var balanceToSave = getBalance(week, driverId, driversTransactions);
                final var balance = balanceAddPort.add(balanceToSave);
                final var balanceCalculationResult = getBalanceCalculationResult(driversTransactions, balance);
                domain.getResults().add(balanceCalculationResult);
                System.out.printf(
                    "Balance for Driver with id: %d and week %d was calculated.\n",
                    driverId, week.weekNumber());
              });
    }*/
    balanceCalculationAddPort.add(domain);
    final var calculationEndTime = System.currentTimeMillis();
    final var calculationDuration = calculationEndTime - calculationStartTime;
    System.out.printf("----> Time: Balance Calculation took %d milli seconds \n", calculationDuration);
  }

  private BalanceCalculationResult getBalanceCalculationResult(
     final List<TransactionResponse> driversTransactions,
     final  Balance savedBalance) {
    final var transactionIds =
        driversTransactions.stream().map(TransactionResponse::getId).collect(toSet());
    final var result =
        BalanceCalculationResult.builder()
            .balance(savedBalance)
            .transactionIds(transactionIds)
            .build();
    return result;
  }

  private List<TransactionResponse> getAllTransactionsByDriverAndWeek(final QWeekResponse week, final Long driverId){
    final var filter = PeriodAndDriverFilter.builder()
            .dateStart(week.getStart())
            .dateEnd(week.getEnd())
            .driverId(driverId)
            .build();

    return transactionQuery.getAllByFilter(filter)
            .stream()
            .toList();
  }

  private Balance getBalance(
      final QWeekResponse week,
      final Long driverId,
      final List<TransactionResponse> driversTransactions) {
    final var previousWeekNumber = week.getNumber() - 1;
    final var previousWeekBalance =
        balanceLoadPort.loadByDriverIdAndYearAndWeekNumberOrDefault(driverId, week.getYear(), previousWeekNumber);

    final var amount = calculateAmount(previousWeekBalance,driversTransactions);
    final var feeAmount = calculateFeeAmount(previousWeekBalance, driversTransactions);

    return Balance.builder()
        .driverId(driverId)
        .qWeekId(week.getId())
        .created(LocalDate.now())
        .amount(amount)
        .fee(feeAmount)
        .build();
  }

  private BigDecimal calculateAmount(
          final Balance previousWeekBalance,
          final List<TransactionResponse> driversTransactions){
    final var transactionsSum = driversTransactions.stream()
                    .filter(transaction -> isNotFeeType(transaction.getType()) )
                    .map(TransactionResponse::getRealAmount)
                    .reduce(BigDecimal::add)
                    .orElse(ZERO);
    final var amountFromPreviousWeek = previousWeekBalance.getAmount();

    return transactionsSum.add(amountFromPreviousWeek);
  }

  private BigDecimal calculateFeeAmount(final Balance previousWeekBalance, final List<TransactionResponse> driversTransactions){
    final var feeTransactionsSum = driversTransactions.stream()
                    .filter(transaction -> isFeeType(transaction.getType()) )
                    .map(TransactionResponse::getRealAmount)
                    .reduce(BigDecimal::add)
                    .orElse(ZERO);
    final var feeAmountFromPreviousWeek = previousWeekBalance.getFee();

    return feeTransactionsSum.add(feeAmountFromPreviousWeek);
  }
}
