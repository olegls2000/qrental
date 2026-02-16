package ee.qrent.billing.car.core.mapper;

import ee.qrent.billing.car.api.in.response.BrandingVerificationCalculationSummaryResponse;
import ee.qrent.billing.car.domain.BrandingVerificationCalculationSummaryRow;

public class BrandingVerificationCalculationSummaryResponseMapper {
  public BrandingVerificationCalculationSummaryResponse toResponse(
      final BrandingVerificationCalculationSummaryRow row) {
    return BrandingVerificationCalculationSummaryResponse.builder()
        .calculationId(row.getCalculationId())
        .actionDate(row.getActionDate())
        .type(row.getType())
        .verificationsCount(row.getVerificationsCount())
        .build();
  }
}
