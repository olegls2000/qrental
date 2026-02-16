package ee.qrent.billing.car.persistence.repository;

import ee.qrent.billing.car.persistence.entity.jakarta.BrandingVerificationCalculationJakartaEntity;

public interface BrandingVerificationCalculationRepository {
  BrandingVerificationCalculationJakartaEntity save(
      final BrandingVerificationCalculationJakartaEntity entity);
}
