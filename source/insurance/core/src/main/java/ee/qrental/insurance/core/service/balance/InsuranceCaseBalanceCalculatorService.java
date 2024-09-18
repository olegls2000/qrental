package ee.qrental.insurance.core.service.balance;

import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.insurance.api.out.InsuranceCaseBalanceLoadPort;
import ee.qrental.insurance.domain.InsuranceCase;
import ee.qrental.insurance.domain.InsuranceCaseBalance;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ee.qrental.transaction.api.in.utils.TransactionTypeConstant.*;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.util.Optional.empty;

@AllArgsConstructor
public class InsuranceCaseBalanceCalculatorService implements InsuranceCaseBalanceCalculator {

  private final String DAMAGE_COMPENSATION_TRANSACTION_TYPE_NAME = "damage payment";
  private final String SELF_RESPONSIBILITY_COMPENSATION_TRANSACTION_TYPE_NAME =
      "self responsibility payment";
  private static final BigDecimal DEFAULT_SELF_RESPONSIBILITY = BigDecimal.valueOf(500);
  private static final BigDecimal PERCENTAGE_FROM_RENT_AMOUNT = BigDecimal.valueOf(0.25d);
  private static final BigDecimal Q_KASKO_DISCOUNT_RATE = BigDecimal.valueOf(2);

  private final InsuranceCaseBalanceLoadPort insuranceCaseBalanceLoadPort;
  private final GetCarLinkQuery carLinkQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetTransactionTypeQuery transactionTypeQuery;
  private final InsuranceCaseBalanceDeriveService deriveService;
  private final TransactionAddUseCase transactionAddUseCase;

  @Override
  public InsuranceCaseBalance calculateBalance(
      final InsuranceCase insuranceCase, final QWeekResponse requestedQWeek) {
    final var driverId = insuranceCase.getDriverId();
    final var requestedQWeekId = requestedQWeek.getId();
    final var requestedWeekBalance = getInsuranceCaseBalance(insuranceCase, requestedQWeekId);
    final var damageTransaction =
        getDamageTransaction(driverId, requestedQWeek, requestedWeekBalance);
    final var selfResponsibilityTransactionOpt =
        getSelfResponsibilityTransactionOpt(driverId, requestedQWeek);
    deriveService.derive(requestedWeekBalance, damageTransaction, selfResponsibilityTransactionOpt);
    saveDamageTransaction(damageTransaction, requestedWeekBalance);
    saveSelfResponsibilityTransaction(selfResponsibilityTransactionOpt, requestedWeekBalance);

    return requestedWeekBalance;
  }

  private void saveDamageTransaction(
      TransactionAddRequest damageTransaction, InsuranceCaseBalance requestedWeekBalance) {
    final var transactionId = transactionAddUseCase.add(damageTransaction);
    if (transactionId != null) {
      requestedWeekBalance.getTransactionIds().add(transactionId);
    }
  }

  private void saveSelfResponsibilityTransaction(
      final Optional<TransactionAddRequest> selfResponsibilityTransactionOpt,
      final InsuranceCaseBalance requestedWeekBalance) {
    if (selfResponsibilityTransactionOpt.isEmpty()) {

      return;
    }
    final var transactionId = transactionAddUseCase.add(selfResponsibilityTransactionOpt.get());
    if (transactionId != null) {
      requestedWeekBalance.getTransactionIds().add(transactionId);
    }
  }

  @Override
  public void setFirstRemainingAndSelfResponsibility(
      final InsuranceCase insuranceCase, final InsuranceCaseBalance insuranceCaseBalance) {
    final var originalDamage = insuranceCase.getDamageAmount();
    if (originalDamage.compareTo(DEFAULT_SELF_RESPONSIBILITY) <= 0) {
      insuranceCaseBalance.setSelfResponsibilityRemaining(ZERO);
      insuranceCaseBalance.setDamageRemaining(originalDamage);
    } else {
      var damageRemaining = originalDamage.subtract(DEFAULT_SELF_RESPONSIBILITY);
      if (insuranceCase.getWithQKasko()) {
        damageRemaining = damageRemaining.divide(Q_KASKO_DISCOUNT_RATE);
      }
      insuranceCaseBalance.setSelfResponsibilityRemaining(DEFAULT_SELF_RESPONSIBILITY);
      insuranceCaseBalance.setDamageRemaining(damageRemaining);
    }
  }

  private InsuranceCaseBalance getInsuranceCaseBalance(
      final InsuranceCase insuranceCase, final Long qWeekId) {
    final var result =
        InsuranceCaseBalance.builder()
            .insuranceCase(insuranceCase)
            .transactionIds(new ArrayList<>())
            .qWeekId(qWeekId)
            .build();
    final var latestBalance =
        insuranceCaseBalanceLoadPort.loadLatestByInsuranceCaseId(insuranceCase.getId());

    if (latestBalance == null) {
      setFirstRemainingAndSelfResponsibility(insuranceCase, result);

      return result;
    }

    result.setDamageRemaining(latestBalance.getDamageRemaining());
    result.setSelfResponsibilityRemaining(latestBalance.getSelfResponsibilityRemaining());

    return result;
  }

  private TransactionAddRequest getDamageTransaction(
      final Long driverId,
      final QWeekResponse qWeek,
      final InsuranceCaseBalance insuranceCaseBalance) {
    var amountForDamageCompensation = getDamageCompensationAmount(driverId, qWeek.getId());

    final var activeCarLink = carLinkQuery.getActiveLinkByDriverId(driverId);
    if (activeCarLink == null) {
      amountForDamageCompensation = insuranceCaseBalance.getDamageRemaining();
    }

    final var damagePaymentTransaction = new TransactionAddRequest();
    damagePaymentTransaction.setComment(
        "Automatically created transaction for the damage compensation.");
    damagePaymentTransaction.setDriverId(driverId);
    damagePaymentTransaction.setAmount(amountForDamageCompensation);
    damagePaymentTransaction.setTransactionTypeId(
        getTransactionTypeIdByName(DAMAGE_COMPENSATION_TRANSACTION_TYPE_NAME));
    damagePaymentTransaction.setDate(qWeek.getStart());

    return damagePaymentTransaction;
  }

  private Optional<TransactionAddRequest> getSelfResponsibilityTransactionOpt(
      final Long driverId, final QWeekResponse qWeek) {
    final var requestedSelfResponsibilityAmountAbs =
        getRequestedSelfResponsibilityAmountAbs(driverId, qWeek.getId());
    if (requestedSelfResponsibilityAmountAbs.compareTo(ZERO) == 0) {

      return empty();
    }
    final var selfResponsibilityTransaction = new TransactionAddRequest();
    selfResponsibilityTransaction.setComment(
        "Automatically created transaction for the Self Responsibility Payment Request compensation.");
    selfResponsibilityTransaction.setDriverId(driverId);
    selfResponsibilityTransaction.setAmount(requestedSelfResponsibilityAmountAbs);
    selfResponsibilityTransaction.setTransactionTypeId(
        getTransactionTypeIdByName(SELF_RESPONSIBILITY_COMPENSATION_TRANSACTION_TYPE_NAME));
    selfResponsibilityTransaction.setDate(qWeek.getStart());

    return Optional.of(selfResponsibilityTransaction);
  }

  private BigDecimal getDamageCompensationAmount(final Long driverId, final Long qWeekId) {
    final var rentAndNonLabelFineTransactionsAmountAbs =
        getAbsRentAndNonLabelFineTransactionsAmountAbs(driverId, qWeekId);

    return rentAndNonLabelFineTransactionsAmountAbs.multiply(PERCENTAGE_FROM_RENT_AMOUNT);
  }

  private BigDecimal getAbsRentAndNonLabelFineTransactionsAmountAbs(
      final Long driverId, final Long qWeekId) {
    return transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId).stream()
        .filter(
            transaction ->
                List.of(TRANSACTION_TYPE_NAME_WEEKLY_RENT, TRANSACTION_TYPE_NO_LABEL_FINE)
                    .contains(transaction.getType()))
        .map(TransactionResponse::getRealAmount)
        .reduce(ZERO, BigDecimal::add)
        .abs();
  }

  final Long getTransactionTypeIdByName(final String transactionTypeName) {
    final var transactionType = transactionTypeQuery.getByName(transactionTypeName);
    if (transactionType == null) {
      throw new RuntimeException(
          format(
              "Transaction Type with name: %s, does not exist. "
                  + "Please create it, before Insurance Case Damage calculation",
              transactionTypeName));
    }
    return transactionType.getId();
  }

  private BigDecimal getRequestedSelfResponsibilityAmountAbs(
      final Long driverId, final Long qWeekId) {
    return transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId).stream()
        .filter(transaction -> TRANSACTION_TYPE_SELF_RESPONSIBILITY.equals(transaction.getType()))
        .map(TransactionResponse::getRealAmount)
        .reduce(ZERO, BigDecimal::add)
        .abs();
  }
}
