package ee.qrent.billing.insurance.core.service.balance;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.insurance.api.in.query.GetInsuranceCaseQuery;
import ee.qrent.billing.insurance.api.in.query.GetQKaskoQuery;
import ee.qrent.billing.insurance.api.out.InsuranceCaseBalanceLoadPort;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import ee.qrent.billing.insurance.domain.InsuranceCaseBalance;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.billing.transaction.api.in.request.TransactionAddRequest;
import ee.qrent.billing.transaction.api.in.usecase.TransactionAddUseCase;

import java.math.BigDecimal;
import java.util.ArrayList;

import static java.lang.Boolean.FALSE;
import static java.math.BigDecimal.ZERO;

public class InsuranceCaseBalanceWithoutQKaskoCalculationStrategy
    extends AbstractInsuranceCaseBalanceCalculationStrategy {

  private final GetInsuranceCaseQuery insuranceCaseQuery;

  public InsuranceCaseBalanceWithoutQKaskoCalculationStrategy(
      final GetQWeekQuery qWeekQuery,
      final GetQKaskoQuery qKaskoQuery,
      final GetTransactionTypeQuery transactionTypeQuery,
      final TransactionAddUseCase transactionAddUseCase,
      final InsuranceCaseBalanceLoadPort insuranceCaseBalanceLoadPort,
      final GetInsuranceCaseQuery insuranceCaseQuery) {
    super(
        qWeekQuery,
        qKaskoQuery,
        transactionTypeQuery,
        transactionAddUseCase,
        insuranceCaseBalanceLoadPort);
    this.insuranceCaseQuery = insuranceCaseQuery;
  }

  @Override
  public boolean canApply(final Long driverId, final Long qWeekId) {

    return !getQKaskoQuery().hasQKasko(driverId, qWeekId);
  }

  @Override
  public InsuranceCaseBalance calculate(
      final InsuranceCase insuranceCase, final QWeekResponse requestedQWeek) {
    final var requestedQWeekId = requestedQWeek.getId();
    final var driverId = insuranceCase.getDriverId();
    final var originalDamageAmount = insuranceCase.getDamageAmount();
    final var previousWeekBalance =
        getPreviousWeekInsuranceCaseBalance(insuranceCase, requestedQWeekId);
    var damageAmount = ZERO;
    if (previousWeekBalance == null) {
      damageAmount = originalDamageAmount;
    } else {
      final var paidAmount =
          insuranceCaseQuery.getPaidAmountByInsuranceCaseId(insuranceCase.getId());
      damageAmount = originalDamageAmount.subtract(paidAmount);
    }
    final var damageTransaction = getDamageTransaction(driverId, requestedQWeek, damageAmount);
    final var requestedBalance =
        InsuranceCaseBalance.builder()
            .insuranceCase(insuranceCase)
            .transactionIds(new ArrayList<>())
            .qWeekId(requestedQWeekId)
            .withQKasko(FALSE)
            .damageRemaining(ZERO)
            .selfResponsibilityRemaining(ZERO)
            .build();
    saveTransactionAndAddToBalance(damageTransaction, requestedBalance);

    return requestedBalance;
  }

  @Override
  public void setRemainingAndSelfResponsibilityFirstTime(
      final InsuranceCase insuranceCase, final InsuranceCaseBalance insuranceCaseBalance) {
    insuranceCaseBalance.setDamageRemaining(insuranceCase.getDamageAmount());
    insuranceCaseBalance.setSelfResponsibilityRemaining(ZERO);
  }

  private TransactionAddRequest getDamageTransaction(
      final Long driverId, final QWeekResponse qWeek, final BigDecimal damageRemaining) {
    final var addRequest = new TransactionAddRequest();
    addRequest.setComment(
        "No Kasko. Leak amount from Insurance Case or Previous Balance. Automatically created transaction.");
    addRequest.setDriverId(driverId);
    addRequest.setAmount(damageRemaining);
    final var transactionTypeNameForDamage = "damage payment";
    addRequest.setTransactionTypeId(getTransactionTypeIdByName(transactionTypeNameForDamage));
    addRequest.setDate(qWeek.getStart());

    return addRequest;
  }
}
