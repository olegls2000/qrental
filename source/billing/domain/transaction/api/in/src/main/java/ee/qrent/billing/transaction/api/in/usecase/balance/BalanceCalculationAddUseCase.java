package ee.qrent.billing.transaction.api.in.usecase.balance;


import ee.qrent.billing.transaction.api.in.request.balance.BalanceCalculationAddRequest;

public interface BalanceCalculationAddUseCase {

  void add(final BalanceCalculationAddRequest request);
}
