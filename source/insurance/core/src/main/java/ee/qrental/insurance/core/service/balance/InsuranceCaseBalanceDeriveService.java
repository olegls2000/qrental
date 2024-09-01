package ee.qrental.insurance.core.service.balance;

import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.TRANSACTION_TYPE_SELF_RESPONSIBILITY_OVERPAYMENT;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.insurance.domain.InsuranceCaseBalance;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseBalanceDeriveService {

  private final TransactionAddUseCase transactionAddUseCase;
  private final GetTransactionTypeQuery transactionTypeQuery;

  public void derive(
      final InsuranceCaseBalance balanceToDerive,
      final TransactionAddRequest damageTransaction,
      final BigDecimal paidSelfResponsibilityAmount,
      final QWeekResponse qWeek) {

    final var driverId = balanceToDerive.getInsuranceCase().getDriverId();

    final var selfResponsibilityRemaining = balanceToDerive.getSelfResponsibilityRemaining();

    // Balance.selfResponsibilityRemaining = 400, paidSelfResponsibility amount = 300,
    // -> Balance.selfResponsibilityRemaining = 100
    if (selfResponsibilityRemaining.compareTo(paidSelfResponsibilityAmount) >= 0) {
      final var updatedSelfResponsibilityRemaining =
          selfResponsibilityRemaining.subtract(paidSelfResponsibilityAmount);
      balanceToDerive.setSelfResponsibilityRemaining(updatedSelfResponsibilityRemaining);
    }

    // Balance.selfResponsibilityRemaining = 100, paidSelfResponsibility amount = 300 (200 is
    // overpayment), new positive transaction: 300
    // -> Balance.selfResponsibilityRemaining = 0, rest of money goes to the new positive
    // transaction
    if (selfResponsibilityRemaining.compareTo(paidSelfResponsibilityAmount) < 0) {
      final var updatedSelfResponsibilityRemaining = ZERO;
      balanceToDerive.setSelfResponsibilityRemaining(updatedSelfResponsibilityRemaining);

      final var overpaymentAmount =
          paidSelfResponsibilityAmount.subtract(selfResponsibilityRemaining);
      final var selfResponsibilityOverpaymentTransaction =
          getTransactionAddRequest(qWeek, driverId, overpaymentAmount);

      transactionAddUseCase.add(selfResponsibilityOverpaymentTransaction);
    }

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

    // In case of all financial debt for the Insurance Case is paid, Insurance case become inactive
    if (balanceToDerive.getDamageRemaining().compareTo(ZERO) == 0
        && balanceToDerive.getSelfResponsibilityRemaining().compareTo(ZERO) == 0) {
      balanceToDerive.getInsuranceCase().setActive(false);
    }
  }

  private TransactionAddRequest getTransactionAddRequest(
      QWeekResponse qWeek, Long driverId, BigDecimal overpaymentAmount) {
    final var selfResponsibilityOverpaymentTransaction = new TransactionAddRequest();
    selfResponsibilityOverpaymentTransaction.setComment(
        "Automatically created transaction for the compensation self responsibility overpayment.");
    selfResponsibilityOverpaymentTransaction.setDriverId(driverId);
    selfResponsibilityOverpaymentTransaction.setAmount(overpaymentAmount);
    selfResponsibilityOverpaymentTransaction.setTransactionTypeId(
        getSelfResponsibilityOverPaymentTransactionTypeId());
    selfResponsibilityOverpaymentTransaction.setDate(qWeek.getStart());
    return selfResponsibilityOverpaymentTransaction;
  }

  final Long getSelfResponsibilityOverPaymentTransactionTypeId() {
    final var selfResponsibilityOverpaymentTransactionType =
        transactionTypeQuery.getByName(TRANSACTION_TYPE_SELF_RESPONSIBILITY_OVERPAYMENT);
    if (selfResponsibilityOverpaymentTransactionType == null) {
      throw new RuntimeException(
          format(
              "Transaction Type with name: %s, does not exist. "
                  + "Please create it, before Insurance Case Damage calculation",
              TRANSACTION_TYPE_SELF_RESPONSIBILITY_OVERPAYMENT));
    }
    return selfResponsibilityOverpaymentTransactionType.getId();
  }
}
