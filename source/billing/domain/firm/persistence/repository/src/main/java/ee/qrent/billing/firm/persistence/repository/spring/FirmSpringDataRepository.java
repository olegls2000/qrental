package ee.qrent.billing.firm.persistence.repository.spring;

import ee.qrent.billing.firm.persistence.entity.jakarta.FirmJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirmSpringDataRepository
        extends JpaRepository<FirmJakartaEntity, Long> {
}
