package ee.qrental.driver.repository.spring;

import ee.qrental.driver.entity.jakarta.CallSignJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallSignSpringDataRepository extends JpaRepository<CallSignJakartaEntity, Long> {
  CallSignJakartaEntity findByCallSign(final Integer callSign);
}
