package ee.qrent.billing.insurance.core.service.balance;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.insurance.api.in.query.GetQKaskoQuery;
import ee.qrent.billing.insurance.api.out.InsuranceCaseBalanceLoadPort;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import ee.qrent.billing.insurance.domain.InsuranceCaseBalance;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.billing.transaction.api.in.request.TransactionAddRequest;
import ee.qrent.billing.transaction.api.in.usecase.TransactionAddUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

@AllArgsConstructor
public abstract class AbstractInsuranceCaseBalanceCalculationStrategy
    implements InsuranceCaseBalanceCalculationStrategy {

  private final GetQWeekQuery qWeekQuery;

  @Getter(AccessLevel.PROTECTED)
  private final GetQKaskoQuery qKaskoQuery;

  private final GetTransactionTypeQuery transactionTypeQuery;
  private final TransactionAddUseCase transactionAddUseCase;
  private final InsuranceCaseBalanceLoadPort insuranceCaseBalanceLoadPort;

  protected InsuranceCaseBalance getPreviousWeekInsuranceCaseBalance(
      final InsuranceCase insuranceCase, final Long requestedWeekId) {
    final var previousWeekId = getPreviousWeekId(requestedWeekId);
    return insuranceCaseBalanceLoadPort.loadByInsuranceCaseIdAndQWeekId(
        insuranceCase.getId(), previousWeekId);
  }

  protected void saveTransactionAndAddToBalance(
      final TransactionAddRequest transactionAddRequest,
      final InsuranceCaseBalance requestedWeekBalance) {
    if (transactionAddRequest.getAmount().compareTo(ZERO) == 0) {

      return;
    }

    final var transactionId = transactionAddUseCase.add(transactionAddRequest);
    if (transactionId != null) {
      requestedWeekBalance.getTransactionIds().add(transactionId);
    }
  }

  protected Long getTransactionTypeIdByName(final String transactionTypeName) {
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

  private Long getPreviousWeekId(final Long qWeekId) {
    final var previousWeek = qWeekQuery.getOneBeforeById(qWeekId);
    if (previousWeek == null) {
      throw new RuntimeException("Previous week not found for qWeekId: " + qWeekId);
    }
    return previousWeek.getId();
  }
}
