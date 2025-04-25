package ee.qrent.billing.transaction.core.service.balance.calculator;

import static ee.qrent.billing.transaction.domain.kind.TransactionKindsCode.*;
import static ee.qrent.common.utils.QNumberUtils.qRound;
import static java.lang.Boolean.FALSE;
import static java.math.BigDecimal.ZERO;
import static lombok.AccessLevel.PROTECTED;

import ee.qrent.billing.constant.api.in.query.GetConstantQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.driver.api.in.response.DriverResponse;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.transaction.domain.balance.Balance;
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
  public BalanceWrapper calculateBalance(
      final DriverResponse driver,
      final QWeekResponse requestedQWeek,
      final Balance previousWeekBalance,
      final Map<String, List<TransactionResponse>> transactionsByKind) {

    final var driverId = driver.getId();
    final var feeAmountForPreviousWeek =
        qRound(getFeeAmountForPreviousWeek(driver, previousWeekBalance));

    handleFeeTransaction(feeAmountForPreviousWeek, requestedQWeek, driverId, transactionsByKind);

    final var feeAmountCurrentWeek =
        getBalanceAmount(
            transactionsByKind.get(F.name()), Balance::getFeeAmount, previousWeekBalance);
    final var nonFeeAbleAmountCurrentWeek =
        getBalanceAmount(
            transactionsByKind.get(NFA.name()), Balance::getNonFeeAbleAmount, previousWeekBalance);
    final var feeAbleAmountCurrentWeek =
        getBalanceAmount(
            transactionsByKind.get(FA.name()), Balance::getFeeAbleAmount, previousWeekBalance);
    final var repairmentAmountCurrentWeek =
        getBalanceAmount(
            transactionsByKind.get(R.name()), Balance::getRepairmentAmount, previousWeekBalance);
    final var positiveAmountCurrentWeek =
        getBalanceAmount(
            transactionsByKind.get(P.name()), Balance::getPositiveAmount, previousWeekBalance);

    final var balance =
        Balance.builder()
            .driverId(driverId)
            .qWeekId(requestedQWeek.getId())
            .created(LocalDate.now())
            .feeAmount(feeAmountCurrentWeek)
            .nonFeeAbleAmount(nonFeeAbleAmountCurrentWeek)
            .feeAbleAmount(feeAbleAmountCurrentWeek)
            .repairmentAmount(repairmentAmountCurrentWeek)
            .positiveAmount(positiveAmountCurrentWeek)
            .derived(FALSE)
            .build();

    saveBalanceIfNecessary(balance);
    final var balanceDerived = getDeriveService().getDerivedBalance(balance);
    final var derivedBalance = saveAndGetDerivedBalanceIfNecessary(balanceDerived);
    return BalanceWrapper.builder()
        .requestedWeekBalance(derivedBalance)
        .transactionsByKind(transactionsByKind)
        .build();
  }

  protected abstract void handleFeeTransaction(
      final BigDecimal feeAmountForPreviousWeek,
      final QWeekResponse requestedQWeek,
      final Long driverId,
      final Map<String, List<TransactionResponse>> transactionsByKind);

  protected abstract void saveBalanceIfNecessary(final Balance balance);

  protected abstract Balance saveAndGetDerivedBalanceIfNecessary(final Balance derivedBalance);

  protected BigDecimal getFeeAmountForPreviousWeek(
      final DriverResponse driver, final Balance previousWeekBalance) {
    if (previousWeekBalance == null) {
      return ZERO;
    }

    if (driver.getNeedFee()) {
      BigDecimal feeAmountForRequestedWeek;
      final var faAmountFromPreviousWeekBalance = previousWeekBalance.getFeeAbleAmount();
      if (faAmountFromPreviousWeekBalance.compareTo(FEE_CALCULATION_THRESHOLD) > 0) {
        final var weeklyInterest = constantQuery.getFeeWeeklyInterest();
        final var nominalWeeklyFee = faAmountFromPreviousWeekBalance.multiply(weeklyInterest);
        final var feeAmountFromPreviousWeek = previousWeekBalance.getFeeAmount();
        final var totalFeeDebt = nominalWeeklyFee.add(feeAmountFromPreviousWeek);
        if (totalFeeDebt.compareTo(faAmountFromPreviousWeekBalance) < 0) {
          feeAmountForRequestedWeek = nominalWeeklyFee;
        } else {
          final var feeOverdue = totalFeeDebt.subtract(faAmountFromPreviousWeekBalance);
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
    var transactionAmountSum = ZERO;
    if (transactions != null) {
      transactionAmountSum =
          transactions.stream()
              .map(TransactionResponse::getRealAmount)
              .map(BigDecimal::abs)
              .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    if (previousWeekBalance == null) {
      return qRound(transactionAmountSum);
    }

    return qRound(transactionAmountSum.add(getAmount.apply(previousWeekBalance)));
  }
}
