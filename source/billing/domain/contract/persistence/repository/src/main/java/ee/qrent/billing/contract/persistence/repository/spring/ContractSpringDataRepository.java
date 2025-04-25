package ee.qrent.billing.contract.persistence.repository.spring;

import ee.qrent.billing.contract.persistence.entity.jakarta.ContractJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ContractSpringDataRepository extends JpaRepository<ContractJakartaEntity, Long> {

  ContractJakartaEntity findByNumber(final String number);

  @Query(
      value =
          "select ct.* from contract ct "
              + "where ct.driver_id = :driverId "
              + "order by ct.created desc limit 1",
      nativeQuery = true)
  ContractJakartaEntity findLatestByDriverId(@Param("driverId") final Long driverId);

  @Query(
      value =
          "SELECT ct.* FROM contract ct "
              + "where ct.date_start <= :date "
              + "and (ct.date_end is null or ct.date_end > :date)",
      nativeQuery = true)
  List<ContractJakartaEntity> findActiveByDate(@Param("date") final LocalDate date);

  @Query(
      value =
          "SELECT ct.* FROM contract ct "
              + "where ct.date_start <= :date "
              + "and (ct.date_end is null or ct.date_end > :date) "
              + "and ct.driver_id = :driverId",
      nativeQuery = true)
  ContractJakartaEntity findActiveByDateAndDriverId(
      @Param("date") final LocalDate date, @Param("driverId") final Long driverId);

  @Query(
      value =
          "SELECT count(*) FROM contract ct "
              + "where ct.date_start <= :date "
              + "and (ct.date_end is null or ct.date_end > :date)",
      nativeQuery = true)
  Long findCountActiveByDate(@Param("date") final LocalDate date);

  @Query(
      value =
          "SELECT ct.* FROM contract ct "
              + "where ct.date_end is not null and ct.date_end <= :date",
      nativeQuery = true)
  List<ContractJakartaEntity> findClosedByDate(@Param("date") final LocalDate date);

  @Query(
      value =
          "SELECT count(*) FROM contract ct "
              + "where ct.date_end is not null and ct.date_end <= :date",
      nativeQuery = true)
  Long findCountClosedByDate(@Param("date") final LocalDate date);

  @Query(value = "SELECT ct.* FROM contract ct where ct.driver_id = :driverId", nativeQuery = true)
  List<ContractJakartaEntity> findAllByDriverId(@Param("driverId") final Long driverId);
}
