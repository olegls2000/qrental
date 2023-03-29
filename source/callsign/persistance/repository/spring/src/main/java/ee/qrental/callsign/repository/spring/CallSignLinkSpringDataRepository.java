package ee.qrental.callsign.repository.spring;

import ee.qrental.callsign.entity.jakarta.CallSignLinkJakartaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallSignLinkSpringDataRepository extends JpaRepository<CallSignLinkJakartaEntity, Long> {

    List<CallSignLinkJakartaEntity> findAllByDateEndIsNull();

    CallSignLinkJakartaEntity findFirstByDriverId(final Long driverId);
}