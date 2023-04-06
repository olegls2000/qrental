package ee.qrental.firm.repository.spring;

import ee.qrental.firm.entity.jakarta.FirmJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirmSpringDataRepository
        extends JpaRepository<FirmJakartaEntity, Long> {
}
