package ee.qrental.driver.repository.spring;

import ee.qrental.driver.entity.jakarta.DriverJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriveSpringDataRepository
        extends JpaRepository<DriverJakartaEntity, Long> {
}
