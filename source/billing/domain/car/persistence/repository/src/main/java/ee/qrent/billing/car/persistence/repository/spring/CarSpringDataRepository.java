package ee.qrent.billing.car.persistence.repository.spring;

import ee.qrent.billing.car.persistence.entity.jakarta.CarJakartaEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarSpringDataRepository extends JpaRepository<CarJakartaEntity, Long> {

  @Query(
      value =
          "select * from car c where id in ("
              + "SELECT cl.car_id FROM car_link cl "
              + "where cl.date_start <= :date "
              + "and (cl.date_end is null or cl.date_end > :date) "
              + ")",
      nativeQuery = true)
  List<CarJakartaEntity> findNotAvailableByDate(@Param("date") final LocalDate date);

  List<CarJakartaEntity> findByActive(final boolean active);
}
