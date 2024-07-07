package ee.qrental.bonus.api.in.usecase;

import ee.qrental.bonus.api.in.request.ObligationCalculationAddRequest;

public interface ObligationCalculationAddUseCase {
  void add(final ObligationCalculationAddRequest request);
}
