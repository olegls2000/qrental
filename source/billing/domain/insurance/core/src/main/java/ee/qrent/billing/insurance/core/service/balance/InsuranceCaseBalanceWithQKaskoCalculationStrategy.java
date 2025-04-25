package ee.qrent.billing.insurance.core.service.balance;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.insurance.api.in.query.GetQKaskoQuery;
import ee.qrent.billing.insurance.api.out.InsuranceCaseBalanceLoadPort;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import ee.qrent.billing.insurance.domain.InsuranceCaseBalance;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.billing.transaction.api.in.request.TransactionAddRequest;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.transaction.api.in.usecase.TransactionAddUseCase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static ee.qrent.billing.insurance.core.service.balance.InsuranceCaseBalanceDeriveUtils.derive;
import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeConstant.*;
import static java.lang.Boolean.TRUE;
import static java.math.BigDecimal.ZERO;

public class InsuranceCaseBalanceWithQKaskoCalculationStrategy
    extends AbstractInsuranceCaseBalanceCalculationStrategy {

  private static final BigDecimal DEFAULT_SELF_RESPONSIBILITY = BigDecimal.valueOf(500);
  private static final BigDecimal PERCENTAGE_FROM_RENT_AMOUNT = BigDecimal.valueOf(0.25d);
  private static final BigDecimal Q_KASKO_DISCOUNT_RATE = BigDecimal.valueOf(2);

  private final GetTransactionQuery transactionQuery;

  public InsuranceCaseBalanceWithQKaskoCalculationStrategy(
      GetQWeekQuery qWeekQuery,
      GetQKaskoQuery qKaskoQuery,
      GetTransactionTypeQuery transactionTypeQuery,
      TransactionAddUseCase transactionAddUseCase,
      InsuranceCaseBalanceLoadPort insuranceCaseBalanceLoadPort,
      final GetTransactionQuery transactionQuery) {
    super(
        qWeekQuery,
        qKaskoQuery,
        transactionTypeQuery,
        transactionAddUseCase,
        insuranceCaseBalanceLoadPort);
    this.transactionQuery = transactionQuery;
  }

  @Override
  public boolean canApply(final Long driverId, final Long qWeekId) {

    return getQKaskoQuery().hasQKasko(driverId, qWeekId);
  }

  @Override
  public InsuranceCaseBalance calculate(
      final InsuranceCase insuranceCase, final QWeekResponse requestedQWeek) {
    final var driverId = insuranceCase.getDriverId();
    final var requestedQWeekId = requestedQWeek.getId();
    final var requestedBalance =
        InsuranceCaseBalance.builder()
            .insuranceCase(insuranceCase)
            .transactionIds(new ArrayList<>())
            .qWeekId(requestedQWeek.getId())
            .withQKasko(TRUE)
            .build();
    final var previousBalance =
        getPreviousWeekInsuranceCaseBalance(insuranceCase, requestedQWeekId);
    if (previousBalance == null) {
      setRemainingAndSelfResponsibilityFirstTime(insuranceCase, requestedBalance);
    } else {
      setRemainingAndSelfResponsibilityBasedOnPreviousWeek(previousBalance, requestedBalance);
    }
    final var writeOffTransaction = getDamageWriteOffTransaction(driverId, requestedQWeek);
    final var selfResponsibilityTransaction =
        getSelfResponsibilityTransaction(driverId, requestedQWeek);
    derive(requestedBalance, writeOffTransaction, selfResponsibilityTransaction);
    saveTransactionAndAddToBalance(writeOffTransaction, requestedBalance);
    saveTransactionAndAddToBalance(selfResponsibilityTransaction, requestedBalance);

    return requestedBalance;
  }

  private TransactionAddRequest getSelfResponsibilityTransaction(
      final Long driverId, final QWeekResponse qWeek) {
    var selfResponsibilityRequestedAmount =
        getRequestedSelfResponsibilityAmountAbs(driverId, qWeek.getId());
    final var selfResponsibilityTransaction = new TransactionAddRequest();
    selfResponsibilityTransaction.setComment(
        "Self Responsibility Requested Payment. Automatically created transaction.");
    selfResponsibilityTransaction.setDriverId(driverId);
    selfResponsibilityTransaction.setAmount(selfResponsibilityRequestedAmount);
    final var transactionTypeNameForSelfResponsibility = "self responsibility payment";
    selfResponsibilityTransaction.setTransactionTypeId(
        getTransactionTypeIdByName(transactionTypeNameForSelfResponsibility));
    selfResponsibilityTransaction.setDate(qWeek.getStart());

    return selfResponsibilityTransaction;
  }

  private BigDecimal getRequestedSelfResponsibilityAmountAbs(
      final Long driverId, final Long qWeekId) {
    return transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId).stream()
        .filter(transaction -> TRANSACTION_TYPE_SELF_RESPONSIBILITY.equals(transaction.getType()))
        .map(TransactionResponse::getRealAmount)
        .reduce(ZERO, BigDecimal::add)
        .abs();
  }

  private TransactionAddRequest getDamageWriteOffTransaction(
      final Long driverId, final QWeekResponse qWeek) {
    final var writeOffAmount = getDamageWriteOffAmount(driverId, qWeek.getId());
    final var damageWriteOffTransaction = new TransactionAddRequest();
    damageWriteOffTransaction.setComment(
        "Write off based on Rent. Automatically created transaction for the damage compensation.");
    damageWriteOffTransaction.setDriverId(driverId);
    damageWriteOffTransaction.setAmount(writeOffAmount);
    final var transactionTypeNameForDamage = "damage payment";
    damageWriteOffTransaction.setTransactionTypeId(
        getTransactionTypeIdByName(transactionTypeNameForDamage));
    damageWriteOffTransaction.setDate(qWeek.getStart());

    return damageWriteOffTransaction;
  }

  private BigDecimal getDamageWriteOffAmount(final Long driverId, final Long qWeekId) {
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

  public void setRemainingAndSelfResponsibilityFirstTime(
      final InsuranceCase insuranceCase, final InsuranceCaseBalance insuranceCaseBalance) {
    final var originalDamage = insuranceCase.getDamageAmount();
    if (originalDamage.compareTo(DEFAULT_SELF_RESPONSIBILITY) <= 0) {
      insuranceCaseBalance.setSelfResponsibilityRemaining(ZERO);
      insuranceCaseBalance.setDamageRemaining(originalDamage);
    } else {
      var damageRemaining = originalDamage.subtract(DEFAULT_SELF_RESPONSIBILITY);
      damageRemaining = damageRemaining.divide(Q_KASKO_DISCOUNT_RATE);
      insuranceCaseBalance.setSelfResponsibilityRemaining(DEFAULT_SELF_RESPONSIBILITY);
      insuranceCaseBalance.setDamageRemaining(damageRemaining);
    }
  }

  public void setRemainingAndSelfResponsibilityBasedOnPreviousWeek(
      final InsuranceCaseBalance previousWeekBalance,
      final InsuranceCaseBalance requestedWeekBalance) {
    requestedWeekBalance.setDamageRemaining(previousWeekBalance.getDamageRemaining());
    requestedWeekBalance.setSelfResponsibilityRemaining(
        previousWeekBalance.getSelfResponsibilityRemaining());
  }
}
