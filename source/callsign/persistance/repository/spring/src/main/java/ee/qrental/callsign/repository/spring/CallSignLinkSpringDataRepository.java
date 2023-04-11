package ee.qrental.callsign.repository.spring;

import ee.qrental.callsign.entity.jakarta.CallSignLinkJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CallSignLinkSpringDataRepository
    extends JpaRepository<CallSignLinkJakartaEntity, Long> {
  @Query(
      value = "SELECT csl.* FROM call_sign_link csl where csl.call_sign_id = :callSignId",
      nativeQuery = true)
  List<CallSignLinkJakartaEntity> findAllByCallSignId(final Long callSignId);

  @Query(
      value =
          "SELECT csl.* FROM call_sign_link csl "
              + "where csl.date_end is null "
              + "and csl.call_sign_id = :callSignId",
      nativeQuery = true)
  CallSignLinkJakartaEntity findOneByDateEndIsNullAndCallSignId(final Long callSignId);

  @Query(
          value =
                  "SELECT csl.* FROM call_sign_link csl "
                          + "where csl.date_end is null "
                          + "and csl.driver_id = :driverId",
          nativeQuery = true)
  CallSignLinkJakartaEntity findOneByDateEndIsNullAndDriverId(final Long driverId);
}
