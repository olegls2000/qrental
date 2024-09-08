package ee.qrental.insurance.core.service.balance;

import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;

public class InsuranceCaseBalanceCalculationStrategySaving
    extends AbstractInsuranceCaseBalanceCalculator {
  private final TransactionAddUseCase transactionAddUseCase;

  public InsuranceCaseBalanceCalculationStrategySaving(
      final GetCarLinkQuery carLinkQuery,
      TransactionAddUseCase transactionAddUseCase,
      GetTransactionQuery transactionQuery,
      GetTransactionTypeQuery transactionTypeQuery,
      InsuranceCaseBalanceDeriveService deriveService) {
    super(carLinkQuery, transactionQuery, transactionTypeQuery, deriveService);
    this.transactionAddUseCase = transactionAddUseCase;
  }

  @Override
  protected Long saveTransactionIfNecessary(final TransactionAddRequest damageTransaction) {
    return transactionAddUseCase.add(damageTransaction);
  }

  @Override
  public boolean canApply(final String strategyName) {
    return SAVING.equals(strategyName);
  }
}
