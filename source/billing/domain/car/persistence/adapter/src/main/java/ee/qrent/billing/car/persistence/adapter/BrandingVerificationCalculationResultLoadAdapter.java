package ee.qrent.billing.car.persistence.adapter;

import ee.qrent.billing.car.api.out.BrandingVerificationCalculationResultLoadPort;
import ee.qrent.billing.car.domain.BrandingVerificationCalculationResultRow;
import ee.qrent.billing.car.domain.BrandingVerificationCalculationSummaryRow;
import ee.qrent.billing.car.persistence.repository.BrandingVerificationCalculationResultProjection;
import ee.qrent.billing.car.persistence.repository.BrandingVerificationCalculationResultRepository;
import ee.qrent.billing.car.persistence.repository.BrandingVerificationCalculationSummaryProjection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BrandingVerificationCalculationResultLoadAdapter
    implements BrandingVerificationCalculationResultLoadPort {

  private final BrandingVerificationCalculationResultRepository repository;

  @Override
  public List<BrandingVerificationCalculationSummaryRow> loadAllCalculations() {
    return repository.findAllCalculations().stream()
        .map(this::mapToDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<BrandingVerificationCalculationResultRow> loadAllResults() {
    return repository.findAllResults().stream()
        .map(this::mapToDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<BrandingVerificationCalculationResultRow> loadResultsByCalculationId(final Long id) {
    return repository.findResultsByCalculationId(id).stream()
        .map(this::mapToDomain)
        .collect(Collectors.toList());
  }

  private BrandingVerificationCalculationResultRow mapToDomain(
      final BrandingVerificationCalculationResultProjection projection) {
    return BrandingVerificationCalculationResultRow.builder()
        .calculationId(projection.getCalculationId())
        .actionDate(projection.getActionDate())
        .comment(projection.getComment())
        .type(projection.getType())
        .verificationId(projection.getVerificationId())
        .verificationDate(projection.getVerificationDate())
        .driverId(projection.getDriverId())
        .driverFirstName(projection.getDriverFirstName())
        .driverLastName(projection.getDriverLastName())
        .carId(projection.getCarId())
        .carRegNumber(projection.getCarRegNumber())
        .carLinkId(projection.getCarLinkId())
        .carLinkStartDate(projection.getCarLinkStartDate())
        .brandingExpirationDate(projection.getBrandingExpirationDate())
        .build();
  }

  private BrandingVerificationCalculationSummaryRow mapToDomain(
      final BrandingVerificationCalculationSummaryProjection projection) {
    return BrandingVerificationCalculationSummaryRow.builder()
        .calculationId(projection.getCalculationId())
        .actionDate(projection.getActionDate())
        .type(projection.getType())
        .verificationsCount(projection.getVerificationsCount())
        .build();
  }
}
