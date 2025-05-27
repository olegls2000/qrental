package ee.qrent.billing.driver.persistence.repository.spring;

import java.time.LocalDate;
import java.util.List;

import ee.qrent.billing.driver.persistence.entity.jakarta.FirmLinkJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FirmLinkSpringDataRepository extends JpaRepository<FirmLinkJakartaEntity, Long> {
  @Query(
      value =
          "SELECT fl.* FROM firm_link fl where  fl.driver_id = :driverId and fl.date_start <= :date and (fl.date_end is null or fl.date_end >= :date)",
      nativeQuery = true)
  FirmLinkJakartaEntity findOneByDriverIdAndDate(
      @Param("driverId") final Long driverId, @Param("date") final LocalDate date);
}
