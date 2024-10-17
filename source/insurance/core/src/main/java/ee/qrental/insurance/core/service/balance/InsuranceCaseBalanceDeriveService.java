package ee.qrental.insurance.core.service.balance;

import static java.math.BigDecimal.ZERO;
import ee.qrental.insurance.domain.InsuranceCaseBalance;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;

import java.math.BigDecimal;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseBalanceDeriveService {

  public void derive(
      final InsuranceCaseBalance balanceToDerive,
      final TransactionAddRequest damageTransaction,
      final Optional<TransactionAddRequest> selfResponsibilityTransactionOpt) {

    deriveSelfResponsibilityAmounts(
        balanceToDerive, selfResponsibilityTransactionOpt, damageTransaction);
    deriveDamageAmounts(balanceToDerive, damageTransaction);
  }

  private static void deriveSelfResponsibilityAmounts(
      final InsuranceCaseBalance balanceToDerive,
      final Optional<TransactionAddRequest> selfResponsibilityTransactionOpt,
      final TransactionAddRequest damageTransaction) {

    if (selfResponsibilityTransactionOpt.isEmpty()) {
      final var selfResponsibilityRemaining = balanceToDerive.getSelfResponsibilityRemaining();
      final var automaticDamageChargeAbs = damageTransaction.getAmount().abs();

      // Balance.selfResponsibilityRemaining = 50, automatic damage charge = 100
      // -> Balance.selfResponsibilityRemaining = 0,  -> Balance.damageRemaining += 50
      if (selfResponsibilityRemaining.compareTo(automaticDamageChargeAbs) <= 0) {
        deriveIfSelfResponsibilityLessThenAutomaticAmount(
            balanceToDerive, selfResponsibilityRemaining);
        return;
      }
      deriveIfSelfResponsibilityGraterThenAutomaticAmount(
          balanceToDerive, automaticDamageChargeAbs);
      return;
    }
    final var selfResponsibilityTransaction = selfResponsibilityTransactionOpt.get();
    final var selfResponsibilityRemaining = balanceToDerive.getSelfResponsibilityRemaining();
    final var selfResponsibilityAmount = selfResponsibilityTransaction.getAmount();

    // Balance.selfResponsibilityRemaining = 400, paidSelfResponsibility amount = 300,
    // -> Balance.selfResponsibilityRemaining = 100, transactional amount = 300
    if (selfResponsibilityRemaining.compareTo(selfResponsibilityAmount) >= 0) {
      final var updatedSelfResponsibilityRemaining =
          selfResponsibilityRemaining.subtract(selfResponsibilityAmount);
      balanceToDerive.setSelfResponsibilityRemaining(updatedSelfResponsibilityRemaining);
    } else if (selfResponsibilityRemaining.compareTo(selfResponsibilityAmount) < 0) {
      final var updatedSelfResponsibilityRemaining = ZERO;
      balanceToDerive.setSelfResponsibilityRemaining(updatedSelfResponsibilityRemaining);
      selfResponsibilityTransaction.setAmount(selfResponsibilityRemaining);
    }
  }

  private static void deriveIfSelfResponsibilityLessThenAutomaticAmount(
      InsuranceCaseBalance balanceToDerive, BigDecimal selfResponsibilityRemaining) {
    balanceToDerive.setSelfResponsibilityRemaining(ZERO);
    final var updatedDamageRemaining =
        balanceToDerive.getDamageRemaining().add(selfResponsibilityRemaining);
    balanceToDerive.setDamageRemaining(updatedDamageRemaining);
  }

  private static void deriveIfSelfResponsibilityGraterThenAutomaticAmount(
      InsuranceCaseBalance balanceToDerive, BigDecimal automaticDamageChargeAbs) {
    // Balance.selfResponsibilityRemaining = 150, automatic damage charge = 100
    // -> Balance.selfResponsibilityRemaining = 50,  -> Balance.damageRemaining += 100
    final var updatedSelfResponsibilityRemaining =
        balanceToDerive.getSelfResponsibilityRemaining().subtract(automaticDamageChargeAbs);
    balanceToDerive.setSelfResponsibilityRemaining(updatedSelfResponsibilityRemaining);

    final var updatedDamageRemaining =
        balanceToDerive.getDamageRemaining().add(automaticDamageChargeAbs);
    balanceToDerive.setDamageRemaining(updatedDamageRemaining);
  }

  private static void deriveDamageAmounts(
      final InsuranceCaseBalance balanceToDerive, final TransactionAddRequest damageTransaction) {
    final var damageRemaining = balanceToDerive.getDamageRemaining();
    final var damageTransactionAmount = damageTransaction.getAmount();
    // Balance.damageRemaining = 100, transactional amount = 20,
    // -> Balance.damageRemaining = 80
    if (damageRemaining.compareTo(damageTransactionAmount) >= 0) {
      final var updatedDamageRemaining = damageRemaining.subtract(damageTransactionAmount);
      balanceToDerive.setDamageRemaining(updatedDamageRemaining);
    }

    // Balance.damageRemaining = 10, transactional amount = 25,
    // -> Balance.damageRemaining = 0, transactional amount = 10
    if (damageRemaining.compareTo(damageTransactionAmount) < 0) {
      final var updatedDamageRemaining = ZERO;
      balanceToDerive.setDamageRemaining(updatedDamageRemaining);
      damageTransaction.setAmount(damageRemaining);
    }
  }
}
