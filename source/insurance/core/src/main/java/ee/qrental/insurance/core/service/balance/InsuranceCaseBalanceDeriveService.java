package ee.qrental.insurance.core.service.balance;

import static java.math.BigDecimal.ZERO;
import ee.qrental.insurance.domain.InsuranceCaseBalance;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseBalanceDeriveService {

  public void derive(
      final InsuranceCaseBalance balanceToDerive,
      final TransactionAddRequest damageTransaction,
      final TransactionAddRequest selfResponsibilityTransaction) {

    final var automaticWriteOffAmount = damageTransaction.getAmount();

    final var unwrittenOffAmount =
        deriveDamageAndGetUnwrittenOffAmount(
            balanceToDerive, damageTransaction, automaticWriteOffAmount);

    deriveSelfResponsibility(balanceToDerive, selfResponsibilityTransaction, unwrittenOffAmount);
  }

  private static void deriveSelfResponsibility(
      final InsuranceCaseBalance balanceToDerive,
      final TransactionAddRequest selfResponsibilityTransaction,
      final BigDecimal unwrittenOffAmount) {
    final var selfResponsibilityRemaining = balanceToDerive.getSelfResponsibilityRemaining();
    final var selfResponsibilityRequestedAmount = selfResponsibilityTransaction.getAmount();

    if (selfResponsibilityRequestedAmount.compareTo(ZERO) != 0) {
      // Balance.selfResponsibilityRemaining = 400, selfResponsibilityRequestedAmount  = 300,
      // -> Balance.selfResponsibilityRemaining = 100, transactional amount = 300
      if (selfResponsibilityRemaining.compareTo(selfResponsibilityRequestedAmount) >= 0) {
        final var updatedSelfResponsibilityRemaining =
            selfResponsibilityRemaining.subtract(selfResponsibilityRequestedAmount);
        balanceToDerive.setSelfResponsibilityRemaining(updatedSelfResponsibilityRemaining);
        selfResponsibilityTransaction.setAmount(selfResponsibilityRequestedAmount);

        return;
      }

      balanceToDerive.setSelfResponsibilityRemaining(ZERO);
      selfResponsibilityTransaction.setAmount(selfResponsibilityRemaining);

      return;
    }

    // Balance.selfResponsibilityRemaining = 150, unwrittenOffAmount = 100
    // -> Balance.selfResponsibilityRemaining = 50,  -> transaction = 100
    if (selfResponsibilityRemaining.compareTo(unwrittenOffAmount) >= 0) {
      final var updatedSelfResponsibilityRemaining =
          selfResponsibilityRemaining.subtract(unwrittenOffAmount);
      balanceToDerive.setSelfResponsibilityRemaining(updatedSelfResponsibilityRemaining);
      selfResponsibilityTransaction.setAmount(unwrittenOffAmount);

      return;
    }

    // Balance.selfResponsibilityRemaining = 50, unwrittenOffAmount = 100
    // -> Balance.selfResponsibilityRemaining = 0,  -> transaction = 50

    balanceToDerive.setSelfResponsibilityRemaining(ZERO);
    selfResponsibilityTransaction.setAmount(selfResponsibilityRemaining);
  }

  private static BigDecimal deriveDamageAndGetUnwrittenOffAmount(
      final InsuranceCaseBalance balanceToDerive,
      final TransactionAddRequest damageTransaction,
      final BigDecimal automaticWriteOffAmount) {
    final var damageRemaining = balanceToDerive.getDamageRemaining();
    // Balance.damageRemaining = 100, automaticWriteOffAmount  = 20,
    // -> Balance.damageRemaining = 80
    if (damageRemaining.compareTo(automaticWriteOffAmount) >= 0) {
      final var updatedDamageRemaining = damageRemaining.subtract(automaticWriteOffAmount);
      balanceToDerive.setDamageRemaining(updatedDamageRemaining);

      return ZERO;
    }

    // Balance.damageRemaining = 10, automaticWriteOffAmount = 20,
    // -> Balance.damageRemaining = 0, over payment = 10
    balanceToDerive.setDamageRemaining(ZERO);
    final var unwrittenOffAmount = automaticWriteOffAmount.subtract(damageRemaining);
    damageTransaction.setAmount(damageRemaining);

    return unwrittenOffAmount;
  }
}
