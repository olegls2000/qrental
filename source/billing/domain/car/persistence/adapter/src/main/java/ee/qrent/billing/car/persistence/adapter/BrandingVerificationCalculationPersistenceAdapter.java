package ee.qrent.billing.car.persistence.adapter;

import ee.qrent.billing.car.api.out.BrandingVerificationCalculationAddPort;
import ee.qrent.billing.car.domain.BrandingVerificationCalculation;
import ee.qrent.billing.car.domain.BrandingVerificationCalculationResult;
import ee.qrent.billing.car.persistence.entity.jakarta.BrandingVerificationCalculationJakartaEntity;
import ee.qrent.billing.car.persistence.entity.jakarta.BrandingVerificationCalculationResultJakartaEntity;
import ee.qrent.billing.car.persistence.entity.jakarta.BrandingVerificationJakartaEntity;
import ee.qrent.billing.car.persistence.repository.BrandingVerificationCalculationRepository;
import ee.qrent.billing.car.persistence.repository.BrandingVerificationCalculationResultRepository;
import ee.qrent.billing.car.persistence.repository.BrandingVerificationRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BrandingVerificationCalculationPersistenceAdapter
    implements BrandingVerificationCalculationAddPort {

  private final BrandingVerificationCalculationRepository calculationRepository;
  private final BrandingVerificationRepository verificationRepository;
  private final BrandingVerificationCalculationResultRepository resultRepository;

  @Override
  public BrandingVerificationCalculation add(final BrandingVerificationCalculation domain) {
    final var calculationEntity =
        BrandingVerificationCalculationJakartaEntity.builder()
            .actionDate(domain.getActionDate())
            .comment(domain.getComment())
            .type(domain.getType())
            .build();
    final var savedCalculation = calculationRepository.save(calculationEntity);
    saveResults(domain, savedCalculation);

    return BrandingVerificationCalculation.builder().id(savedCalculation.getId()).build();
  }

  private void saveResults(
      final BrandingVerificationCalculation domain,
      final BrandingVerificationCalculationJakartaEntity savedCalculation) {
    if (domain.getResults() == null) {
      return;
    }
    for (final BrandingVerificationCalculationResult result : domain.getResults()) {
      final var verification = result.getVerification();
      final var verificationEntity =
          BrandingVerificationJakartaEntity.builder()
              .date(verification.getDate())
              .driverId(verification.getDriverId())
              .carId(verification.getCarId())
              .carLinkId(verification.getCarLinkId())
              .brandingExpirationDate(verification.getBrandingExpirationDate())
              .build();
      final var savedVerification = verificationRepository.save(verificationEntity);
      final var resultEntity =
          BrandingVerificationCalculationResultJakartaEntity.builder()
              .calculation(savedCalculation)
              .verification(savedVerification)
              .build();
      resultRepository.save(resultEntity);
    }
  }
}
