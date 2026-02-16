package ee.qrent.billing.car.core.mapper;

import ee.qrent.billing.car.api.in.response.BrandingVerificationCalculationResultResponse;
import ee.qrent.billing.car.domain.BrandingVerificationCalculationResultRow;

public class BrandingVerificationCalculationResultResponseMapper {
  public BrandingVerificationCalculationResultResponse toResponse(
      final BrandingVerificationCalculationResultRow row) {
    return BrandingVerificationCalculationResultResponse.builder()
        .calculationId(row.getCalculationId())
        .actionDate(row.getActionDate())
        .comment(row.getComment())
        .type(row.getType())
        .verificationId(row.getVerificationId())
        .verificationDate(row.getVerificationDate())
        .driverId(row.getDriverId())
        .driverFirstName(row.getDriverFirstName())
        .driverLastName(row.getDriverLastName())
        .carId(row.getCarId())
        .carRegNumber(row.getCarRegNumber())
        .carLinkId(row.getCarLinkId())
        .carLinkStartDate(row.getCarLinkStartDate())
        .brandingExpirationDate(row.getBrandingExpirationDate())
        .build();
  }
}
