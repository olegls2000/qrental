package ee.qrental.transaction.api.in.usecase.balance;


import ee.qrental.transaction.api.in.request.balance.BalanceCalculationAddRequest;

public interface BalanceCalculationAddUseCase {

  void add(final BalanceCalculationAddRequest request);
}
