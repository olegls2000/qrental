package ee.qrental.transaction.core.service.balance;

import static ee.qrental.transaction.api.in.TransactionConstants.isFeeType;
import static ee.qrental.transaction.api.in.TransactionConstants.isNotFeeType;
import static java.math.BigDecimal.ZERO;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

import ee.qrental.common.core.utils.QTimeUtils;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodAndDriverFilter;
import ee.qrental.transaction.api.in.request.balance.BalanceCalculationAddRequest;
import ee.qrental.transaction.api.in.response.TransactionResponse;
// import jakarta.transaction.Transactional;
import ee.qrental.transaction.api.in.usecase.balance.BalanceCalculationAddUseCase;
import ee.qrental.transaction.api.out.balance.BalanceAddPort;
import ee.qrental.transaction.api.out.balance.BalanceCalculationAddPort;
import ee.qrental.transaction.api.out.balance.BalanceCalculationLoadPort;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.api.out.type.TransactionTypeLoadPort;
import ee.qrental.transaction.core.mapper.balance.BalanceCalculationAddRequestMapper;
import ee.qrental.transaction.domain.balance.Balance;
import ee.qrental.transaction.domain.balance.BalanceCalculation;
import ee.qrental.transaction.domain.balance.BalanceCalculationResult;
import ee.qrental.transaction.domain.type.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationService implements BalanceCalculationAddUseCase {

  private static final LocalDate DEFAULT_START_DATE = LocalDate.of(2023, Month.JANUARY, 02);

  private final GetQWeekQuery qWeekQuery;
  private final GetDriverQuery driverQuery;
  private final GetTransactionQuery transactionQuery;
  private final TransactionTypeLoadPort transactionTypeLoadPort;
  private final FeeCalculationService feeCalculationService;
  private final ReplenishService replenishService;
  private final BalanceCalculationAddPort balanceCalculationAddPort;
  private final BalanceAddPort balanceAddPort;
  private final BalanceLoadPort balanceLoadPort;
  private final BalanceCalculationLoadPort balanceCalculationLoadPort;
  private final BalanceCalculationAddRequestMapper addRequestMapper;

  // @Transactional
  @Override
  public void add(final BalanceCalculationAddRequest addRequest) {
    final var calculationStartTime = System.currentTimeMillis();
    final var latestRequestedWeekId = addRequest.getQWeekId();
    final var domain = addRequestMapper.toDomain(addRequest);
    final var latestRequestedWeek = qWeekQuery.getById(latestRequestedWeekId);
    var latestCalculatedWeek = getLatestCalculatedWeek();
    setStartAndEndDates(domain, latestCalculatedWeek, latestRequestedWeek);
    final var qWeeksForCalculation =
        getQWeeksForCalculationOrdered(latestCalculatedWeek, latestRequestedWeek);

    qWeeksForCalculation.stream()
        .forEach(
            week ->
                driverQuery.getAll().stream()
                    .forEach(
                        driver -> {
                          final var driverId = driver.getId();
                          replenishService.replenishFee(week, driverId);
                          replenishService.replenishNonFeeAble(week, driverId);
                          feeCalculationService.calculate(week, driver);
                          final var driversTransactions =
                              getAllTransactionsByDriverAndWeek(week, driverId);
                          final var balanceToSave = getBalance(week, driverId, driversTransactions);
                          final var balance = balanceAddPort.add(balanceToSave);
                          final var balanceCalculationResult =
                              getBalanceCalculationResult(driversTransactions, balance);
                          domain.getResults().add(balanceCalculationResult);
                          System.out.printf(
                              "Balance for Driver with id: %d and week %d was calculated.\n",
                              driverId, week.getNumber());
                        }));
    balanceCalculationAddPort.add(domain);
    final var calculationEndTime = System.currentTimeMillis();
    final var calculationDuration = calculationEndTime - calculationStartTime;
    System.out.printf(
        "----> Time: Balance Calculation took %d milli seconds \n", calculationDuration);
  }

  private void setStartAndEndDates(
      final BalanceCalculation domain,
      QWeekResponse latestCalculatedWeek,
      QWeekResponse latestRequestedWeek) {
    final var startDate =
        latestCalculatedWeek == null
            ? DEFAULT_START_DATE
            : latestCalculatedWeek.getEnd().plus(1, DAYS);
    final var endDate = latestRequestedWeek.getEnd();

    domain.setStartDate(startDate);
    domain.setEndDate(endDate);
  }

  private List<QWeekResponse> getQWeeksForCalculationOrdered(
      final QWeekResponse lastCalculationWeek, final QWeekResponse latestRequestedWeek) {
    final var qWeeksForCalculation =
        lastCalculationWeek == null
            ? qWeekQuery.getAllBeforeById(latestRequestedWeek.getId())
            : qWeekQuery.getAllBetweenByIds(
                lastCalculationWeek.getId(), latestRequestedWeek.getId());
    qWeeksForCalculation.sort(
        comparing(QWeekResponse::getYear).thenComparing(QWeekResponse::getNumber));

    return qWeeksForCalculation;
  }

  private QWeekResponse getLatestCalculatedWeek() {
    var lastCalculationDate = balanceCalculationLoadPort.loadLastCalculatedDate();
    if (lastCalculationDate == null) {

      return null;
    }
    final var year = lastCalculationDate.getYear();
    final var weekNumber = QTimeUtils.getWeekNumber(lastCalculationDate);

    return qWeekQuery.getByYearAndNumber(year, weekNumber);
  }

  private BalanceCalculationResult getBalanceCalculationResult(
      final List<TransactionResponse> driversTransactions, final Balance savedBalance) {
    final var transactionIds =
        driversTransactions.stream().map(TransactionResponse::getId).collect(toSet());
    final var result =
        BalanceCalculationResult.builder()
            .balance(savedBalance)
            .transactionIds(transactionIds)
            .build();
    return result;
  }

  private List<TransactionResponse> getAllTransactionsByDriverAndWeek(
      final QWeekResponse week, final Long driverId) {
    final var filter =
        PeriodAndDriverFilter.builder()
            .dateStart(week.getStart())
            .dateEnd(week.getEnd())
            .driverId(driverId)
            .build();

    return transactionQuery.getAllByFilter(filter).stream().toList();
  }

  private Balance getBalance(
      final QWeekResponse week,
      final Long driverId,
      final List<TransactionResponse> driversTransactions) {
    final var previousWeekNumber = week.getNumber() - 1;
    final var previousWeekBalance =
        balanceLoadPort.loadByDriverIdAndYearAndWeekNumberOrDefault(
            driverId, week.getYear(), previousWeekNumber);

    final var feeAbleTransactionTypes =
        transactionTypeLoadPort.loadFeeAble().stream().map(TransactionType::getName).toList();

    final var nonFeeAbleTransactionTypes =
        transactionTypeLoadPort.loadNonFeeAble().stream().map(TransactionType::getName).toList();

    final var feeAbleAmount =
        calculateAmount(previousWeekBalance, driversTransactions, feeAbleTransactionTypes);
    final var nonFeeAbleAmount =
        calculateAmount(previousWeekBalance, driversTransactions, nonFeeAbleTransactionTypes);
    final var feeAmount = calculateFeeAmount(previousWeekBalance, driversTransactions);

    return Balance.builder()
        .driverId(driverId)
        .qWeekId(week.getId())
        .created(LocalDate.now())
        .feeAbleAmount(feeAbleAmount)
        .nonFeeAbleAmount(nonFeeAbleAmount)
        .fee(feeAmount)
        .build();
  }

  private BigDecimal calculateAmount(
      final Balance previousWeekBalance,
      final List<TransactionResponse> driversTransactions,
      final List<String> includedTypes) {
    final var transactionsSum =
        driversTransactions.stream()
            .filter(transaction -> isNotFeeType(transaction.getType()))
            .filter(transaction -> includedTypes.contains(transaction.getType()))
            .map(TransactionResponse::getRealAmount)
            .reduce(BigDecimal::add)
            .orElse(ZERO);
    final var amountFromPreviousWeek = previousWeekBalance.getFeeAbleAmount();

    return transactionsSum.add(amountFromPreviousWeek);
  }

  private BigDecimal calculateFeeAmount(
      final Balance previousWeekBalance, final List<TransactionResponse> driversTransactions) {
    final var feeTransactionsSum =
        driversTransactions.stream()
            .filter(transaction -> isFeeType(transaction.getType()))
            .map(TransactionResponse::getRealAmount)
            .reduce(BigDecimal::add)
            .orElse(ZERO);
    final var feeAmountFromPreviousWeek = previousWeekBalance.getFee();

    return feeTransactionsSum.add(feeAmountFromPreviousWeek);
  }
}
