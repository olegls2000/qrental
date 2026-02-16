package ee.qrent.billing.car.api.in.query;

import ee.qrent.billing.car.api.in.response.BrandingVerificationCalculationResultResponse;
import ee.qrent.billing.car.api.in.response.BrandingVerificationCalculationSummaryResponse;
import java.util.List;

public interface GetBrandingVerificationCalculationQuery {
  List<BrandingVerificationCalculationSummaryResponse> getAllCalculations();

  List<BrandingVerificationCalculationResultResponse> getResultsByCalculationId(Long id);
}
