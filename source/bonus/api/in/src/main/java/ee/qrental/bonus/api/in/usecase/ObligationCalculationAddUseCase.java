package ee.qrental.bonus.api.in.usecase;

import ee.qrental.bonus.api.in.request.ObligationCalculationAddRequest;
import ee.qrental.common.core.in.usecase.AddUseCase;

public interface ObligationCalculationAddUseCase {
  void add(final ObligationCalculationAddRequest request);
}
