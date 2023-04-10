package ee.qrental.callsign.repository.spring;

import ee.qrental.callsign.entity.jakarta.CallSignLinkJakartaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CallSignLinkSpringDataRepository
    extends JpaRepository<CallSignLinkJakartaEntity, Long> {

  List<CallSignLinkJakartaEntity> findAllByDateEndIsNull();

  CallSignLinkJakartaEntity findFirstByDriverId(final Long driverId);

  @Query(
      value =
          "SELECT cs.call_sign FROM call_sign_link csl "
              + "LEFT JOIN call_sign cs ON csl.call_sign_id = cs.id "
              + " where csl.driver_id = :driverId",
      nativeQuery = true)
  Integer getCallSignByDriverId(final Long driverId);
}
