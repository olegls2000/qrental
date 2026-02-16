package ee.qrent.billing.car.persistence.repository.spring;

import ee.qrent.billing.car.persistence.entity.jakarta.BrandingVerificationCalculationJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandingVerificationCalculationSpringDataRepository
    extends JpaRepository<BrandingVerificationCalculationJakartaEntity, Long> {}
