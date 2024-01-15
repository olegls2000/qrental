package ee.qrental.bonus.repository.spring;

import ee.qrental.bonus.entity.jakarta.ObligationCalculationResultJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObligationCalculationResultSpringDataRepository
    extends JpaRepository<ObligationCalculationResultJakartaEntity, Long> {
}
