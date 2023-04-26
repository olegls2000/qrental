package ee.qrental.balance.api.in.usecase;

import ee.qrental.balance.api.in.request.BalanceCalculationAddRequest;

public interface BalanceCalculationAddUseCase {

  void add(final BalanceCalculationAddRequest request);
}
