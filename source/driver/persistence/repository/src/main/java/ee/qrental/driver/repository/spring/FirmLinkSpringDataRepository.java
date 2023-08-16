package ee.qrental.driver.repository.spring;

import ee.qrental.driver.entity.jakarta.CallSignLinkJakartaEntity;
import java.time.LocalDate;
import java.util.List;

import ee.qrental.driver.entity.jakarta.FirmLinkJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FirmLinkSpringDataRepository
    extends JpaRepository<FirmLinkJakartaEntity, Long> {
  @Query(
      value = "SELECT fl.* FROM firm_link fl where fl.firm_id = :firmId",
      nativeQuery = true)
  List<FirmLinkJakartaEntity> findAllByFirmId(@Param("firmId") final Long firmId);

  @Query(
      value =
          "SELECT fl.* FROM firm_link fl "
              + "where fl.date_end is null "
              + "and fl.firm_id = :firmId",
      nativeQuery = true)
  FirmLinkJakartaEntity findOneByDateEndIsNullAndFirmId(
      @Param("firmId") final Long firmId);

  @Query(
      value =
          "SELECT fl.* FROM firm_link fl "
              + "where fl.date_end is null "
              + "and fl.driver_id = :driverId",
      nativeQuery = true)
  FirmLinkJakartaEntity findOneByDateEndIsNullAndDriverId(
      @Param("driverId") final Long driverId);

  @Query(
          value =
                  "SELECT fl.* FROM firm_link fl "
                          + "where  fl.driver_id = :driverId "
                          + "and fl.date_start <= :requiredDate "
                          + "and (fl.date_end is null or fl.date_end >= :requiredDate)",
          nativeQuery = true)
  FirmLinkJakartaEntity findOneByDriverIdAndRequiredDate(
          @Param("driverId") final Long driverId,
          @Param("requiredDate") final LocalDate requiredDate);
}
