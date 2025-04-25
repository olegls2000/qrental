package ee.qrent.billing.contract.persistence.repository.spring;

import ee.qrent.billing.contract.persistence.entity.jakarta.AbsenceJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AbsenceSpringDataRepository extends JpaRepository<AbsenceJakartaEntity, Long> {
  @Query(
      value =
          "SELECT * FROM absence ab WHERE "
              + "ab.driver_id = :driverId "
              + "AND (ab.date_end IS NULL and ab.date_start < :dateEnd) "
              + "OR (ab.date_start >= :dateStart and ab.date_start <= :dateEnd) "
              + "OR (ab.date_end >= :dateStart and ab.date_end <= :dateEnd);",
      nativeQuery = true)
  List<AbsenceJakartaEntity> findByDriverIdAndDateStartAndDateEnd(
      @Param("driverId") final Long driverId,
      @Param("dateStart") final LocalDate dateStart,
      @Param("dateEnd") final LocalDate dateEnd);

  @Query(
      value =
          "SELECT * FROM absence ab WHERE "
              + "ab.driver_id = :driverId "
              + "AND (ab.date_end IS NULL) "
              + "OR (ab.date_end > :dateStart);",
      nativeQuery = true)
  List<AbsenceJakartaEntity> findByDriverIdAndDateStart(
      @Param("driverId") final Long driverId, @Param("dateStart") final LocalDate dateStart);
}
