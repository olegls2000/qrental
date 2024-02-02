package ee.qrental.bonus.api.in.usecase;

import ee.qrental.bonus.api.in.request.BonusProgramAddRequest;

public interface BonusProgramAddUseCase {
  void add(final BonusProgramAddRequest request);
}
