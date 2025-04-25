package ee.qrent.billing.bonus.api.in.usecase;

import ee.qrent.billing.bonus.api.in.request.BonusProgramAddRequest;

public interface BonusProgramAddUseCase {
  void add(final BonusProgramAddRequest request);
}
