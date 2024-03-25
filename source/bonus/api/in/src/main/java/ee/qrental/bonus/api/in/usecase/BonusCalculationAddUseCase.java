package ee.qrental.bonus.api.in.usecase;

import ee.qrental.bonus.api.in.request.BonusCalculationAddRequest;

public interface BonusCalculationAddUseCase {
  void add(final BonusCalculationAddRequest request);
}
