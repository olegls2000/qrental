package ee.qrental.balance.core.service;

import static ee.qrental.transaction.api.in.TransactionConstants.TRANSACTION_TYPE_NAME_FEE_DEBT;
import static java.lang.Boolean.FALSE;
import static java.math.BigDecimal.ZERO;

import ee.qrental.balance.api.out.BalanceLoadPort;
import ee.qrental.common.core.utils.Week;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.transaction.api.in.query.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FeeTransactionCreator {
  private static final BigDecimal WEEKLY_INTEREST = BigDecimal.valueOf(0.07d);
  private static final BigDecimal FEE_CALCULATION_THRESHOLD = BigDecimal.valueOf(-100);
  private final BalanceLoadPort loadPort;
  private final TransactionAddUseCase transactionAddUseCase;
  private final GetTransactionTypeQuery transactionTypeQuery;

  public Optional<TransactionResponse> creteFeeTransactionIfNecessary(
      final Week week, final DriverResponse driver) {
    if (!driver.getNeedFee()) {
      System.out.println(
          "Fee charging is not activated for Driver (" + driver.getIsikukood() + ")");
      return Optional.empty();
    }
    final var driverId = driver.getId();
    final var feeAmount = getFeeAmountBasedOnPreviousWekBalance(week, driverId);
    if (feeAmount.compareTo(ZERO) == 0) {
      System.out.println(
          "Driver ("
              + driver.getIsikukood()
              + ") doesn't have negative balance for Previous week. Fee debt transaction is not required.");
      return Optional.empty();
    }
    final var feeTransactionAddRequest =
        getTransactionRequest(feeAmount, driverId, week.weekNumber(), week.end());
    final var id = transactionAddUseCase.add(feeTransactionAddRequest);
    System.out.println(
        "Fee transaction with id = "
            + id
            + " was crated for Driver ("
            + driver.getIsikukood()
            + ")");
    return Optional.of(TransactionResponse.builder().id(id).realAmount(feeAmount).build());
  }

  private TransactionAddRequest getTransactionRequest(
      final BigDecimal feeAmount,
      final Long driverId,
      final Integer weekNumber,
      final LocalDate transactionDate) {
    final var feeDebtTransactionType =
        transactionTypeQuery.getByName(TRANSACTION_TYPE_NAME_FEE_DEBT);
    final var feeTransactionAddRequest = new TransactionAddRequest();
    feeTransactionAddRequest.setAmount(feeAmount);
    feeTransactionAddRequest.setDate(transactionDate);
    feeTransactionAddRequest.setWithVat(FALSE);
    feeTransactionAddRequest.setTransactionTypeId(feeDebtTransactionType.getId());
    feeTransactionAddRequest.setDriverId(driverId);
    feeTransactionAddRequest.setWeekNumber(weekNumber);
    feeTransactionAddRequest.setComment("automatically created during Balance calculation");

    return feeTransactionAddRequest;
  }

  private BigDecimal getFeeAmountBasedOnPreviousWekBalance(final Week week, final Long driverId) {
    final var currentWeekNumber = week.weekNumber();
    final var previousWeekNumber = currentWeekNumber - 1;
    final var balanceFromPreviousWeek =
        loadPort.loadByDriverIdAndYearAndWeekNumber(driverId, week.getYear(), previousWeekNumber);
    final var amountFromPreviousWeek = balanceFromPreviousWeek.getAmount();
    if (amountFromPreviousWeek.compareTo(FEE_CALCULATION_THRESHOLD) < 0) {
      System.out.printf(
          "Debt from previous week is bigger then %s EURO (Debt: %s EUR), fee will be calculated",
          FEE_CALCULATION_THRESHOLD, amountFromPreviousWeek);
      return amountFromPreviousWeek.multiply(WEEKLY_INTEREST).negate();
    }

    return ZERO;
  }
}
