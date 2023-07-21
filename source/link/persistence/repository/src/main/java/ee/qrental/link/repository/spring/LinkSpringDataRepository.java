package ee.qrental.link.repository.spring;

import ee.qrental.link.entity.jakarta.LinkJakartaEntity;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LinkSpringDataRepository extends JpaRepository<LinkJakartaEntity, Long> {
  @Query(
      value =
          "SELECT ln.* FROM link ln "
              + "where  ln.driver_id = :driverId "
              + "and ln.date_start <= :nowDate "
              + "and (ln.date_end is null or ln.date_end > :nowDate)",
      nativeQuery = true)
  LinkJakartaEntity findActiveByDriverIdAndNowDate(
      @Param("driverId") final Long driverId, @Param("nowDate") final LocalDate nowDate);
}
