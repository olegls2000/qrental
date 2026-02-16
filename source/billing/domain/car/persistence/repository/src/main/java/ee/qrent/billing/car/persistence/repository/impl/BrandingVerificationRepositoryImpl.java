package ee.qrent.billing.car.persistence.repository.impl;

import ee.qrent.billing.car.persistence.entity.jakarta.BrandingVerificationJakartaEntity;
import ee.qrent.billing.car.persistence.repository.BrandingVerificationRepository;
import ee.qrent.billing.car.persistence.repository.spring.BrandingVerificationSpringDataRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BrandingVerificationRepositoryImpl implements BrandingVerificationRepository {

  private final BrandingVerificationSpringDataRepository springDataRepository;

  @Override
  public BrandingVerificationJakartaEntity save(final BrandingVerificationJakartaEntity entity) {
    return springDataRepository.save(entity);
  }
}
