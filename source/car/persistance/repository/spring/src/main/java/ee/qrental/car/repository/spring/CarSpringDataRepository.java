package ee.qrental.car.repository.spring;

import ee.qrental.car.entity.jakarta.CarJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarSpringDataRepository
        extends JpaRepository<CarJakartaEntity, Long> {
}
