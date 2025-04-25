package ee.qrent.billing.transaction.core.service.balance.calculator;

import ee.qrent.billing.transaction.domain.balance.Balance;

import java.time.LocalDate;

import static java.lang.Boolean.TRUE;
import static java.math.BigDecimal.ZERO;

public class BalanceDeriveService {

  public Balance getDerivedBalance(final Balance balanceToDerive) {
    var derivedPositiveAmount = balanceToDerive.getPositiveAmount();
    var derivedFeeAmount = balanceToDerive.getFeeAmount();
    var derivedNonFeeAbleAmount = balanceToDerive.getNonFeeAbleAmount();
    var derivedFeeAbleAmount = balanceToDerive.getFeeAbleAmount();
    // var derivedRepairmentAmount = balanceToDerive.getRepairmentAmount();
    final var derivedBalanceBuilder =
        Balance.builder()
            .derived(TRUE)
            .driverId(balanceToDerive.getDriverId())
            .created(LocalDate.now())
            .qWeekId(balanceToDerive.getQWeekId())
            .repairmentAmount(balanceToDerive.getRepairmentAmount());

    if (derivedPositiveAmount.compareTo(ZERO) == 0) {
      System.out.println(
          "No positive amount. Derive is impossible for the Balance: " + balanceToDerive);

      return derivedBalanceBuilder
          .feeAmount(balanceToDerive.getFeeAmount())
          .nonFeeAbleAmount(derivedNonFeeAbleAmount)
          .feeAbleAmount(derivedFeeAbleAmount)
          .positiveAmount(derivedPositiveAmount)
          .build();
    }

    if (derivedFeeAmount.compareTo(ZERO) == 0
        && derivedFeeAbleAmount.compareTo(ZERO) == 0
        && derivedNonFeeAbleAmount.compareTo(ZERO) == 0) {

      System.out.println(
          "No negative amounts. Derive is not required for Balance: " + balanceToDerive);

      return derivedBalanceBuilder
          .feeAmount(balanceToDerive.getFeeAmount())
          .nonFeeAbleAmount(derivedNonFeeAbleAmount)
          .feeAbleAmount(derivedFeeAbleAmount)
          .positiveAmount(derivedPositiveAmount)
          .build();
    }
    // positive: 5, fee: 2
    if (derivedPositiveAmount.compareTo(derivedFeeAmount) > 0) {
      derivedPositiveAmount = derivedPositiveAmount.subtract(derivedFeeAmount); // 3
      derivedFeeAmount = ZERO; // 0
    } else {
      // positive: 2, fee: 3
      derivedFeeAmount = derivedFeeAmount.subtract(derivedPositiveAmount); // 1
      derivedPositiveAmount = ZERO; // 0
    }

    if (derivedPositiveAmount.compareTo(ZERO) == 0) {
      System.out.println("No positive amount. Derive is done for the Balance: " + balanceToDerive);

      return derivedBalanceBuilder
          .feeAmount(derivedFeeAmount)
          .nonFeeAbleAmount(derivedNonFeeAbleAmount)
          .feeAbleAmount(derivedFeeAbleAmount)
          .positiveAmount(derivedPositiveAmount)
          .build();
    }

    // positive: 5, NonFeeAble: 2
    if (derivedPositiveAmount.compareTo(derivedNonFeeAbleAmount) > 0) {
      derivedPositiveAmount = derivedPositiveAmount.subtract(derivedNonFeeAbleAmount); // 3
      derivedNonFeeAbleAmount = ZERO; // 0
    } else {
      // positive: 2, NonFeeAble: 3
      derivedNonFeeAbleAmount = derivedNonFeeAbleAmount.subtract(derivedPositiveAmount); // 1
      derivedPositiveAmount = ZERO; // 0
    }

    if (derivedPositiveAmount.compareTo(ZERO) == 0) {
      System.out.println("No positive amount. Derive is done for the Balance: " + balanceToDerive);

      return derivedBalanceBuilder
          .feeAmount(derivedFeeAmount)
          .nonFeeAbleAmount(derivedNonFeeAbleAmount)
          .feeAbleAmount(derivedFeeAbleAmount)
          .positiveAmount(derivedPositiveAmount)
          .build();
    }

    // positive: 5, feeAble: 2
    if (derivedPositiveAmount.compareTo(derivedFeeAbleAmount) > 0) {
      derivedPositiveAmount = derivedPositiveAmount.subtract(derivedFeeAbleAmount); // 3
      derivedFeeAbleAmount = ZERO; // 0
    } else {
      // positive: 2, feeAble: 3
      derivedFeeAbleAmount = derivedFeeAbleAmount.subtract(derivedPositiveAmount); // 1
      derivedPositiveAmount = ZERO; // 0
    }

    if (derivedPositiveAmount.compareTo(ZERO) == 0) {
      System.out.println("No positive amount. Derive is done for the Balance: " + balanceToDerive);

      return derivedBalanceBuilder
          .feeAmount(derivedFeeAmount)
          .nonFeeAbleAmount(derivedNonFeeAbleAmount)
          .feeAbleAmount(derivedFeeAbleAmount)
          .positiveAmount(derivedPositiveAmount)
          .build();
    }

    return derivedBalanceBuilder
        .feeAmount(derivedFeeAmount)
        .nonFeeAbleAmount(derivedNonFeeAbleAmount)
        .feeAbleAmount(derivedFeeAbleAmount)
        .positiveAmount(derivedPositiveAmount)
        .build();
  }
}
