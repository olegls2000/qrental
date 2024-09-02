package ee.qrental.insurance.core.service.balance;

import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;

public class InsuranceCaseBalanceCalculationStrategyDryRun
    extends AbstractInsuranceCaseBalanceCalculator {

  public InsuranceCaseBalanceCalculationStrategyDryRun(
      GetCarLinkQuery carLinkQuery,
      GetTransactionQuery transactionQuery,
      GetTransactionTypeQuery transactionTypeQuery,
      InsuranceCaseBalanceDeriveService deriveService) {
    super(carLinkQuery, transactionQuery, transactionTypeQuery, deriveService);
  }

  @Override
  protected void saveTransactionIfNecessary(TransactionAddRequest damageTransaction) {}

  @Override
  public boolean canApply(final String strategyName) {
    return DRY_RUN.equals(strategyName);
  }
}
