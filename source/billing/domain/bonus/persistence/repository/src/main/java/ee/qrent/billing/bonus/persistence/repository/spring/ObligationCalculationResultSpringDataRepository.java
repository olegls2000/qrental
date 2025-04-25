package ee.qrent.billing.bonus.persistence.repository.spring;

import ee.qrent.billing.bonus.persistence.entity.jakarta.ObligationCalculationResultJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObligationCalculationResultSpringDataRepository
    extends JpaRepository<ObligationCalculationResultJakartaEntity, Long> {
}
