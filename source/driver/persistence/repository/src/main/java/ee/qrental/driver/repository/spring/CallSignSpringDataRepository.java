package ee.qrental.driver.repository.spring;

import ee.qrental.driver.entity.jakarta.CallSignJakartaEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CallSignSpringDataRepository extends JpaRepository<CallSignJakartaEntity, Long> {
  CallSignJakartaEntity findByCallSign(final Integer callSign);

  @Query(
      value =
          "SELECT * FROM call_sign cs "
              + "WHERE cs.id not in ("
              + "select csl.call_sign_id from call_sign_link csl "
              + "where csl.date_end is null or csl.date_end > :nowDate)",
      nativeQuery = true)
  List<CallSignJakartaEntity> findAvailable(@Param("nowDate") final LocalDate nowDate);
}
