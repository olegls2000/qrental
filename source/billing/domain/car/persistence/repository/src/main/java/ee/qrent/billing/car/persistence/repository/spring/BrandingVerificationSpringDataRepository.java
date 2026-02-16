package ee.qrent.billing.car.persistence.repository.spring;

import ee.qrent.billing.car.persistence.entity.jakarta.BrandingVerificationJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandingVerificationSpringDataRepository
    extends JpaRepository<BrandingVerificationJakartaEntity, Long> {}
