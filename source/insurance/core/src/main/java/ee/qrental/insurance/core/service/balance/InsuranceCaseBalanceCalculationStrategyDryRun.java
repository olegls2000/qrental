package ee.qrental.insurance.core.service.balance;

import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;

public class InsuranceCaseBalanceCalculationStrategyDryRun
    extends AbstractInsuranceCaseBalanceCalculator {

  public InsuranceCaseBalanceCalculationStrategyDryRun(
      GetTransactionQuery transactionQuery,
      GetTransactionTypeQuery transactionTypeQuery,
      InsuranceCaseBalanceDeriveService deriveService) {
    super(transactionQuery, transactionTypeQuery, deriveService);
  }

  @Override
  protected void saveTransactionIfNecessary(TransactionAddRequest damageTransaction) {}

  @Override
  public boolean canApply(final String strategyName) {
    return DRY_RUN.equals(strategyName);
  }
}
