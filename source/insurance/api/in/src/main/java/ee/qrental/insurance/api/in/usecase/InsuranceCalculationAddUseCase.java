package ee.qrental.insurance.api.in.usecase;

import ee.qrent.common.in.usecase.AddUseCase;
import ee.qrental.insurance.api.in.request.InsuranceCalculationAddRequest;

public interface InsuranceCalculationAddUseCase extends AddUseCase<InsuranceCalculationAddRequest> {
  Long add(final InsuranceCalculationAddRequest request);
}
