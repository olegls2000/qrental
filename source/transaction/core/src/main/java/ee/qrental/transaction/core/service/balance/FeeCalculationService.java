package ee.qrental.transaction.core.service.balance;

import static ee.qrental.transaction.api.in.TransactionConstants.TRANSACTION_TYPE_NAME_FEE_DEBT;
import static java.lang.Boolean.FALSE;
import static java.math.BigDecimal.ZERO;

import ee.qrental.common.core.utils.Week;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FeeCalculationService {
  private static final BigDecimal WEEKLY_INTEREST = BigDecimal.valueOf(0.0175d);
  private static final BigDecimal FEE_CALCULATION_THRESHOLD = ZERO;
  private final BalanceLoadPort loadPort;
  private final TransactionAddUseCase transactionAddUseCase;
  private final GetTransactionTypeQuery transactionTypeQuery;

  public void calculate(
      final Week week, final DriverResponse driver) {
    if (!driver.getNeedFee()) {
      System.out.println(
          "Fee charging is not activated for Driver (" + driver.getIsikukood() + ")");
      return;
    }
    final var driverId = driver.getId();
    final var feeAmount = getFeeAmountBasedOnPreviousWekBalance(week, driverId);
    if (feeAmount.compareTo(ZERO) == 0) {
      System.out.println(
          "Driver ("
              + driver.getIsikukood()
              + ") doesn't have negative balance for Previous week. Fee debt transaction is not required.");
      return;
    }
    final var feeTransactionAddRequest =
        getTransactionRequest(feeAmount, driverId, week.weekNumber(), week.end());
transactionAddUseCase.add(feeTransactionAddRequest);
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
        loadPort.loadByDriverIdAndYearAndWeekNumberOrDefault(driverId, week.getYear(), previousWeekNumber);
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
