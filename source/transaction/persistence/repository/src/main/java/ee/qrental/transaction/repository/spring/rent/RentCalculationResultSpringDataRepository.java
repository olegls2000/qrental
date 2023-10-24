package ee.qrental.transaction.repository.spring.rent;

import ee.qrental.transaction.entity.jakarta.rent.RentCalculationResultJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentCalculationResultSpringDataRepository
    extends JpaRepository<RentCalculationResultJakartaEntity, Long> {}
