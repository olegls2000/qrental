package ee.qrental.callsign.repository.spring;

import ee.qrental.callsign.entity.jakarta.CallSignLinkJakartaEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CallSignLinkSpringDataRepository
    extends JpaRepository<CallSignLinkJakartaEntity, Long> {
  @Query(
      value = "SELECT csl.* FROM call_sign_link csl where csl.call_sign_id = :callSignId",
      nativeQuery = true)
  List<CallSignLinkJakartaEntity> findAllByCallSignId(@Param("callSignId") final Long callSignId);

  @Query(
      value =
          "SELECT csl.* FROM call_sign_link csl "
              + "where csl.date_end is null "
              + "and csl.call_sign_id = :callSignId",
      nativeQuery = true)
  CallSignLinkJakartaEntity findOneByDateEndIsNullAndCallSignId(
      @Param("callSignId") final Long callSignId);

  @Query(
      value =
          "SELECT csl.* FROM call_sign_link csl "
              + "where csl.date_end is null "
              + "and csl.driver_id = :driverId",
      nativeQuery = true)
  CallSignLinkJakartaEntity findOneByDateEndIsNullAndDriverId(
      @Param("driverId") final Long driverId);

  @Query(
      value =
          "SELECT csl.* FROM call_sign_link csl "
              + "where  csl.driver_id = :driverId "
              + "and csl.date_start < :date "
              + "and (csl.date_end is null or csl.date_end > :date)",
      nativeQuery = true)
  CallSignLinkJakartaEntity findOneByDriverIdAndDate(
      @Param("driverId") final Long driverId, @Param("date") final LocalDate date);
}
