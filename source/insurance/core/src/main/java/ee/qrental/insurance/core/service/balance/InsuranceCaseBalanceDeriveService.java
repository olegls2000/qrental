package ee.qrental.insurance.core.service.balance;

import static java.math.BigDecimal.ZERO;
import ee.qrental.insurance.domain.InsuranceCaseBalance;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseBalanceDeriveService {

  public void derive(
      final InsuranceCaseBalance balanceToDerive,
      final TransactionAddRequest damageTransaction,
      final Optional<TransactionAddRequest> selfResponsibilityTransactionOpt) {

    deriveSelfResponsibilityAmounts(balanceToDerive, selfResponsibilityTransactionOpt);
    deriveDamageAmounts(balanceToDerive, damageTransaction);

    // In case of all financial debt for the Insurance Case is paid, Insurance case become inactive
    if (balanceToDerive.getDamageRemaining().compareTo(ZERO) == 0
        && balanceToDerive.getSelfResponsibilityRemaining().compareTo(ZERO) == 0) {
      balanceToDerive.getInsuranceCase().setActive(false);
    }
  }

  private static void deriveDamageAmounts(
      InsuranceCaseBalance balanceToDerive, TransactionAddRequest damageTransaction) {
    final var damageRemaining = balanceToDerive.getDamageRemaining();
    final var damageTransactionAmount = damageTransaction.getAmount();
    // Balance.damageRemaining = 100, transactional amount = 20,
    // -> Balance.damageRemaining = 80
    if (damageRemaining.compareTo(damageTransactionAmount) >= 0) {
      final var updatedDamageRemaining = damageRemaining.subtract(damageTransactionAmount);
      balanceToDerive.setDamageRemaining(updatedDamageRemaining);
    }

    // Balance.damageRemaining = 10, transactional amount = 20,
    // -> Balance.damageRemaining = 0, transactional amount = 10
    if (damageRemaining.compareTo(damageTransactionAmount) < 0) {
      final var updatedDamageRemaining = ZERO;
      balanceToDerive.setDamageRemaining(updatedDamageRemaining);
      final var updatedDamagePaymentTransactionAmount =
          damageTransactionAmount.subtract(damageRemaining);
      damageTransaction.setAmount(updatedDamagePaymentTransactionAmount);
    }
  }

  private static void deriveSelfResponsibilityAmounts(
      final InsuranceCaseBalance balanceToDerive,
      final Optional<TransactionAddRequest> selfResponsibilityTransactionOpt) {

    if (selfResponsibilityTransactionOpt.isEmpty()) {
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
    }

    // Balance.selfResponsibilityRemaining = 400, paidSelfResponsibility amount = 500,
    // -> Balance.selfResponsibilityRemaining = 0, transactional amount = 400
    if (selfResponsibilityRemaining.compareTo(selfResponsibilityAmount) < 0) {
      final var updatedSelfResponsibilityRemaining = ZERO;
      balanceToDerive.setSelfResponsibilityRemaining(updatedSelfResponsibilityRemaining);
      selfResponsibilityTransaction.setAmount(selfResponsibilityRemaining);
    }
  }
}
