package ee.qrent.billing.car.persistence.repository.impl;

import ee.qrent.billing.car.persistence.entity.jakarta.BrandingVerificationCalculationJakartaEntity;
import ee.qrent.billing.car.persistence.repository.BrandingVerificationCalculationRepository;
import ee.qrent.billing.car.persistence.repository.spring.BrandingVerificationCalculationSpringDataRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BrandingVerificationCalculationRepositoryImpl
    implements BrandingVerificationCalculationRepository {

  private final BrandingVerificationCalculationSpringDataRepository springDataRepository;

  @Override
  public BrandingVerificationCalculationJakartaEntity save(
      final BrandingVerificationCalculationJakartaEntity entity) {
    return springDataRepository.save(entity);
  }
}
