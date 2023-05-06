package ee.qrental.balance.core.service;

import static java.lang.Boolean.FALSE;
import static java.math.BigDecimal.ZERO;

import ee.qrental.balance.api.out.BalanceLoadPort;
import ee.qrental.common.core.utils.Week;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodAndDriverFilter;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FeeTransactionCreator {
  public static final String TRANSACTION_TYPE_NAME_FEE_DEBT = "fee debt";
  private static final BigDecimal WEEKLY_INTEREST = BigDecimal.valueOf(0.07d);
  private final BalanceLoadPort loadPort;
  private final TransactionAddUseCase transactionAddUseCase;
  private final GetTransactionTypeQuery transactionTypeQuery;
  private final GetTransactionQuery transactionQuery;

  public void creteFeeTransactionIfNecessary(final Week week, final Long driverId) {
    final var feeAmount = getFeeAmountBasedOnPreviousWekBalance(week, driverId);
    if (feeAmount.compareTo(ZERO) == 0) {
      System.out.println("Fee ebt transaction is not required.");
      return;
    }
    final var feeTransactionAddRequest =
        getTransactionRequest(feeAmount, driverId, week.weekNumber());
    transactionAddUseCase.add(feeTransactionAddRequest);
  }

  public BigDecimal calculateFeeBalance(final Week week, final Long driverId) {
    final var currentWeekNumber = week.weekNumber();
    if (currentWeekNumber == 10 || currentWeekNumber == 11) {
      return ZERO;
    }
    final var previousWeekNumber = currentWeekNumber - 1;
    final var balanceFromPreviousWeek =
        loadPort.loadByDriverIdAndYearAndWeekNumber(driverId, week.getYear(), previousWeekNumber);
    final var feeBalanceFromPreviousWeek = balanceFromPreviousWeek.getFee();

    final var filter =
        PeriodAndDriverFilter.builder()
            .driverId(driverId)
            .dateStart(week.start())
            .datEnd(week.end())
            .build();
    final var feeSum =
        transactionQuery.getAllByFilter(filter).stream()
            .filter(tr -> tr.getType().equals(TRANSACTION_TYPE_NAME_FEE_DEBT))
            .map(tr -> tr.getRealAmount())
            .reduce(BigDecimal::add)
            .get();
    return feeSum.add(feeBalanceFromPreviousWeek);
  }

  private TransactionAddRequest getTransactionRequest(
      final BigDecimal feeAmount, final Long driverId, final Integer weekNumber) {
    final var feeDebtTransactionType =
        transactionTypeQuery.getByName(TRANSACTION_TYPE_NAME_FEE_DEBT);
    final var feeTransactionAddRequest = new TransactionAddRequest();
    feeTransactionAddRequest.setAmount(feeAmount);
    feeTransactionAddRequest.setDate(LocalDate.now());
    feeTransactionAddRequest.setWithVat(FALSE);
    feeTransactionAddRequest.setTransactionTypeId(feeDebtTransactionType.getId());
    feeTransactionAddRequest.setDriverId(driverId);
    feeTransactionAddRequest.setWeekNumber(weekNumber);
    feeTransactionAddRequest.setComment("automatically created during Balance calculation");

    return feeTransactionAddRequest;
  }

  private BigDecimal getFeeAmountBasedOnPreviousWekBalance(final Week week, final Long driverId) {
    final var currentWeekNumber = week.weekNumber();
    if (currentWeekNumber == 10 || currentWeekNumber == 11) {
      return ZERO;
    }
    final var previousWeekNumber = currentWeekNumber - 1;
    final var balanceFromPreviousWeek =
        loadPort.loadByDriverIdAndYearAndWeekNumber(driverId, week.getYear(), previousWeekNumber);
    final var amountFromPreviousWeek = balanceFromPreviousWeek.getAmount();
    if (amountFromPreviousWeek.compareTo(ZERO) < 0) {

      return amountFromPreviousWeek.multiply(WEEKLY_INTEREST).negate();
    }

    return ZERO;
  }
}
