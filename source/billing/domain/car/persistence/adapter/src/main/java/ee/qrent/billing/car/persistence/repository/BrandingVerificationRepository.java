package ee.qrent.billing.car.persistence.repository;

import ee.qrent.billing.car.persistence.entity.jakarta.BrandingVerificationJakartaEntity;

public interface BrandingVerificationRepository {
  BrandingVerificationJakartaEntity save(final BrandingVerificationJakartaEntity entity);
}
