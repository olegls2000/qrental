package ee.qrent.billing.bonus.api.in.usecase;

import ee.qrent.billing.bonus.api.in.request.BonusCalculationAddRequest;

public interface BonusCalculationAddUseCase {
  void add(final BonusCalculationAddRequest request);
}
