package ee.qrental.bonus.repository.spring;

import ee.qrental.bonus.entity.jakarta.BonusCalculationResultJakartaEntity;
import ee.qrental.bonus.entity.jakarta.ObligationCalculationResultJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonusCalculationResultSpringDataRepository
    extends JpaRepository<BonusCalculationResultJakartaEntity, Long> {
}
