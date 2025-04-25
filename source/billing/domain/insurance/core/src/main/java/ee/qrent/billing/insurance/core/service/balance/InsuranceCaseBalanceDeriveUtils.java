package ee.qrent.billing.insurance.core.service.balance;

import static java.math.BigDecimal.ZERO;
import ee.qrent.billing.insurance.domain.InsuranceCaseBalance;
import ee.qrent.billing.transaction.api.in.request.TransactionAddRequest;

import java.math.BigDecimal;

import lombok.experimental.UtilityClass;

@UtilityClass
public class InsuranceCaseBalanceDeriveUtils {

  public static void derive(
      final InsuranceCaseBalance requestedBalance,
      final TransactionAddRequest writeOffTransaction,
      final TransactionAddRequest selfResponsibilityTransaction) {
    final var initialWriteOffAmount = writeOffTransaction.getAmount();
    processSelfResponsibility(requestedBalance, selfResponsibilityTransaction);
    processWriteOff(requestedBalance, writeOffTransaction);
    processExtraWriteOff(
        initialWriteOffAmount,
        requestedBalance,
        writeOffTransaction,
        selfResponsibilityTransaction);
  }

  private static void processExtraWriteOff(
      final BigDecimal initialWriteOffAmount,
      final InsuranceCaseBalance requestedBalance,
      final TransactionAddRequest writeOffTransaction,
      final TransactionAddRequest selfResponsibilityTransaction) {
    final var processedWriteOffAmount = writeOffTransaction.getAmount();
    if (initialWriteOffAmount.compareTo(processedWriteOffAmount) == 0) {
      return;
    }
    final var extraWriteOffAmount = initialWriteOffAmount.subtract(processedWriteOffAmount);
    final var selfResponsibilityRemaining = requestedBalance.getSelfResponsibilityRemaining();

    var updatedSelfResponsibilityRemaining = ZERO;
    var updatedSelfResponsibilityAmount = ZERO;

    // Balance.selfResponsibilityRemaining = 100, selfResponsibility Transaction Amount  = 300,
    // extraWriteOffAmount = 23
    // -> Balance.selfResponsibilityRemaining = 77, transactional SR amount = 323
    if (selfResponsibilityRemaining.compareTo(extraWriteOffAmount) >= 0) {
      updatedSelfResponsibilityRemaining =
          selfResponsibilityRemaining.subtract(extraWriteOffAmount);
      updatedSelfResponsibilityAmount =
          selfResponsibilityTransaction.getAmount().add(extraWriteOffAmount);
    } else {
      // Balance.selfResponsibilityRemaining = 10, selfResponsibility Transaction Amount  = 300,
      // extraWriteOffAmount = 23
      // -> Balance.selfResponsibilityRemaining = 0, transactional SR amount = 310
      updatedSelfResponsibilityRemaining = ZERO;
      updatedSelfResponsibilityAmount =
          selfResponsibilityTransaction.getAmount().add(selfResponsibilityRemaining);
    }
    requestedBalance.setSelfResponsibilityRemaining(updatedSelfResponsibilityRemaining);
    selfResponsibilityTransaction.setAmount(updatedSelfResponsibilityAmount);
  }

  private static void processSelfResponsibility(
      final InsuranceCaseBalance balanceToDerive,
      final TransactionAddRequest selfResponsibilityTransaction) {
    final var selfResponsibilityRemaining = balanceToDerive.getSelfResponsibilityRemaining();
    final var selfResponsibilityRequestedAmount = selfResponsibilityTransaction.getAmount();
    if (selfResponsibilityRequestedAmount.compareTo(ZERO) == 0) {

      return;
    }
    // Balance.selfResponsibilityRemaining = 400, selfResponsibilityRequestedAmount  = 300,
    // -> Balance.selfResponsibilityRemaining = 100, transactional amount = 300
    if (selfResponsibilityRemaining.compareTo(selfResponsibilityRequestedAmount) >= 0) {
      final var updatedSelfResponsibilityRemaining =
          selfResponsibilityRemaining.subtract(selfResponsibilityRequestedAmount);
      balanceToDerive.setSelfResponsibilityRemaining(updatedSelfResponsibilityRemaining);
      selfResponsibilityTransaction.setAmount(selfResponsibilityRequestedAmount);

      return;
    }
    // Balance.selfResponsibilityRemaining = 400, selfResponsibilityRequestedAmount  = 401,
    // -> Balance.selfResponsibilityRemaining = 0, transactional amount = 400
    balanceToDerive.setSelfResponsibilityRemaining(ZERO);
    selfResponsibilityTransaction.setAmount(selfResponsibilityRemaining);
  }

  private static void processWriteOff(
      final InsuranceCaseBalance balanceToDerive, final TransactionAddRequest writeOffTransaction) {
    final var damageRemaining = balanceToDerive.getDamageRemaining();
    final var writeOffAmount = writeOffTransaction.getAmount();

    // Balance.damageRemaining = 100, automaticWriteOffAmount  = 20,
    // -> Balance.damageRemaining = 80,  write Off amount = 20, left over from writeOff:  0
    if (damageRemaining.compareTo(writeOffAmount) >= 0) {
      final var updatedDamageRemaining = damageRemaining.subtract(writeOffAmount);
      balanceToDerive.setDamageRemaining(updatedDamageRemaining);

      return;
    }

    // Balance.damageRemaining = 5, automaticWriteOffAmount = 20,
    // -> Balance.damageRemaining = 0, write Off amount = 5, left over from writeOff:  15
    balanceToDerive.setDamageRemaining(ZERO);
    // final var unwrittenOffAmount = writeOffAmount.subtract(damageRemaining);
    writeOffTransaction.setAmount(damageRemaining);
  }
}
