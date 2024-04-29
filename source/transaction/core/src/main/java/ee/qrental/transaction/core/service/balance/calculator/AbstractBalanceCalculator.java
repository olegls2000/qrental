package ee.qrental.transaction.core.service.balance.calculator;

import static ee.qrental.transaction.domain.kind.TransactionKindsCode.*;
import static java.lang.Boolean.FALSE;
import static java.math.BigDecimal.ZERO;
import static lombok.AccessLevel.PROTECTED;

import ee.qrental.constant.api.in.query.GetConstantQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.domain.balance.Balance;
import ee.qrental.transaction.domain.kind.TransactionKindsCode;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class AbstractBalanceCalculator implements BalanceCalculatorStrategy {
  private static final BigDecimal FEE_CALCULATION_THRESHOLD = ZERO;

  @Getter(value = PROTECTED)
  private final BalanceDeriveService deriveService;

  @Getter(value = PROTECTED)
  private final GetConstantQuery constantQuery;

  @Override
  public Balance calculateBalance(
      final DriverResponse driver,
      final QWeekResponse requestedQWeek,
      final Balance previousWeekBalance,
      final Map<TransactionKindsCode, List<TransactionResponse>> transactionsByKind) {

    final var driverId = driver.getId();
    final var feeAmountForPreviousWeek = getFeeAmountForPreviousWeek(driver, previousWeekBalance);

    final var feeAmountCurrentWeek =
        getBalanceAmount(transactionsByKind.get(F), Balance::getFeeAmount, previousWeekBalance);
    final var nonFeeAbleAmountCurrentWeek =
        getBalanceAmount(
            transactionsByKind.get(NFA), Balance::getNonFeeAbleAmount, previousWeekBalance);
    final var feeAbleAmountCurrentWeek =
        getBalanceAmount(
            transactionsByKind.get(FA), Balance::getFeeAbleAmount, previousWeekBalance);
    final var repairmentAmountCurrentWeek =
        getBalanceAmount(
            transactionsByKind.get(R), Balance::getRepairmentAmount, previousWeekBalance);
    final var positiveAmountCurrentWeek =
        getBalanceAmount(
            transactionsByKind.get(P), Balance::getPositiveAmount, previousWeekBalance);
    final var totalFee = feeAmountForPreviousWeek.add(feeAmountCurrentWeek);

    final var balance =
        Balance.builder()
            .driverId(driverId)
            .qWeekId(requestedQWeek.getId())
            .created(LocalDate.now())
            .feeAmount(totalFee)
            .nonFeeAbleAmount(nonFeeAbleAmountCurrentWeek)
            .feeAbleAmount(feeAbleAmountCurrentWeek)
            .repairmentAmount(repairmentAmountCurrentWeek)
            .positiveAmount(positiveAmountCurrentWeek)
            .derived(FALSE)
            .build();

    final var balanceDerived = getDeriveService().getDerivedBalance(balance);
    saveBalanceIfNecessary(balance);
    saveFeeTransactionIfNecessary(feeAmountForPreviousWeek, requestedQWeek, driverId);

    return saveAndGetDerivedBalanceIfNecessary(balanceDerived);
  }

  protected abstract void saveFeeTransactionIfNecessary(
      BigDecimal feeAmountForPreviousWeek, QWeekResponse requestedQWeek, Long driverId);

  protected abstract void saveBalanceIfNecessary(final Balance balance);

  protected abstract Balance saveAndGetDerivedBalanceIfNecessary(final Balance derivedBalance);

  protected BigDecimal getFeeAmountForPreviousWeek(
      final DriverResponse driver, final Balance previousWeekBalance) {
    if (driver.getNeedFee()) {
      BigDecimal feeAmountForRequestedWeek;
      final var faAmountFromPreviousWeekBalance = previousWeekBalance.getFeeAbleAmount();
      if (faAmountFromPreviousWeekBalance.compareTo(FEE_CALCULATION_THRESHOLD) > 0) {
        final var weeklyInterest = constantQuery.getFeeWeeklyInterest();
        final var nominalWeeklyFee = faAmountFromPreviousWeekBalance.multiply(weeklyInterest);

        final var feeAmountFromPreviousWeek = previousWeekBalance.getFeeAmount();
        final var faAmountFromPreviousWeek = previousWeekBalance.getFeeAbleAmount();
        final var totalFeeDebt = nominalWeeklyFee.add(feeAmountFromPreviousWeek);
        if (totalFeeDebt.compareTo(faAmountFromPreviousWeek) < 0) {
          feeAmountForRequestedWeek = nominalWeeklyFee;
        } else {
          final var feeOverdue = totalFeeDebt.subtract(faAmountFromPreviousWeek);
          feeAmountForRequestedWeek = nominalWeeklyFee.subtract(feeOverdue);
        }

        return feeAmountForRequestedWeek;
      }
      return ZERO;
    }
    return ZERO;
  }

  protected BigDecimal getBalanceAmount(
      final List<TransactionResponse> transactions,
      final Function<Balance, BigDecimal> getAmount,
      final Balance previousWeekBalance) {
    if (transactions == null || transactions.isEmpty()) {
      return ZERO;
    }

    final var transactionAmountSum =
        transactions.stream()
            .map(TransactionResponse::getRealAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    return transactionAmountSum.add(getAmount.apply(previousWeekBalance));
  }
}
