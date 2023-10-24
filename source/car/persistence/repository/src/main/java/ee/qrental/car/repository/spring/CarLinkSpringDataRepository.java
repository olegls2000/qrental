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
          "SELECT ln.* FROM link ln "
              + "where  ln.driver_id = :driverId "
              + "and ln.date_start <= :date "
              + "and (ln.date_end is null or ln.date_end > :date)",
      nativeQuery = true)
  CarLinkJakartaEntity findActiveByDriverIdAndDate(
      @Param("driverId") final Long driverId, @Param("date") final LocalDate date);

  @Query(
      value =
          "SELECT ln.* FROM link ln "
              + "where  ln.car_id = :car_id "
              + "and ln.date_start <= :date "
              + "and (ln.date_end is null or ln.date_end > :date)",
      nativeQuery = true)
  List<CarLinkJakartaEntity> findActiveByCarIdAndDate(
      @Param("car_id") final Long carId, @Param("date") final LocalDate date);

  @Query(
      value =
          "SELECT ln.* FROM link ln "
              + "where ln.date_start <= :date "
              + "and (ln.date_end is null or ln.date_end > :date)",
      nativeQuery = true)
  List<CarLinkJakartaEntity> findActiveByDate(@Param("date") final LocalDate date);
}
