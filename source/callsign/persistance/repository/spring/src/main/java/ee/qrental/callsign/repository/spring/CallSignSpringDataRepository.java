package ee.qrental.callsign.repository.spring;

import ee.qrental.callsign.entity.jakarta.CallSignJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallSignSpringDataRepository extends JpaRepository<CallSignJakartaEntity, Long> {
  CallSignJakartaEntity findByCallSign(final Integer callSign);
}
