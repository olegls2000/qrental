package ee.qrent.billing.bonus.persistence.repository.spring;

import ee.qrent.billing.bonus.persistence.entity.jakarta.BonusCalculationResultJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonusCalculationResultSpringDataRepository
    extends JpaRepository<BonusCalculationResultJakartaEntity, Long> {
}
