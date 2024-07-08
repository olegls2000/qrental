package ee.qrental.car.repository.spring;

import ee.qrental.car.entity.jakarta.CarLinkJakartaEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarLinkSpringDataRepository extends JpaRepository<CarLinkJakartaEntity, Long> {
  @Query(
      value =
          "SELECT cl.* FROM car_link cl "
              + "WHERE  cl.driver_id = :driverId "
              + "AND cl.date_start <= :date "
              + "AND (cl.date_end is null or cl.date_end > :date)",
      nativeQuery = true)
  CarLinkJakartaEntity findActiveByDriverIdAndDate(
      @Param("driverId") final Long driverId, @Param("date") final LocalDate date);

  CarLinkJakartaEntity findFirstByDriverIdOrderByDateStartDesc(final Long driverId);

  @Query(
      value =
          "SELECT cl.* FROM car_link cl "
              + "WHERE  cl.car_id = :car_id "
              + "AND cl.date_start <= :date "
              + "AND (cl.date_end is null or cl.date_end > :date)",
      nativeQuery = true)
  List<CarLinkJakartaEntity> findActiveByCarIdAndDate(
      @Param("car_id") final Long carId, @Param("date") final LocalDate date);

  @Query(
      value =
          "SELECT cl.* FROM car_link cl "
              + "WHERE cl.date_start <= :date "
              + "AND (cl.date_end is null or cl.date_end > :date)",
      nativeQuery = true)
  List<CarLinkJakartaEntity> findActiveByDate(@Param("date") final LocalDate date);

  @Query(
      value =
          "SELECT count(*) FROM car_link cl "
              + "WHERE cl.date_start <= :date "
              + "AND (cl.date_end is null or cl.date_end > :date)",
      nativeQuery = true)
  Long findCountActiveByDate(@Param("date") final LocalDate date);

  @Query(
      value =
          "SELECT cl.* FROM car_link cl " + "WHERE cl.date_end is not null AND cl.date_end < :date",
      nativeQuery = true)
  List<CarLinkJakartaEntity> findClosedByDate(@Param("date") final LocalDate date);

  @Query(
      value =
          "SELECT count(*) FROM car_link cl "
              + "WHERE cl.date_end is not null AND cl.date_end < :date",
      nativeQuery = true)
  Long findCountClosedByDate(@Param("date") final LocalDate date);
}
