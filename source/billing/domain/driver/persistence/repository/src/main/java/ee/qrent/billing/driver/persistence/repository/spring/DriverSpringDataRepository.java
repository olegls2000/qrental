package ee.qrent.billing.driver.persistence.repository.spring;

import ee.qrent.billing.driver.persistence.entity.jakarta.DriverJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DriverSpringDataRepository extends JpaRepository<DriverJakartaEntity, Long> {

  @Query(
      value =
          "select dr.* from driver dr "
              + " LEFT JOIN obligation ob ON ob.driver_id = dr.id "
              + " where ob.match_count = :matchCount and ob.q_week_id = :qWeekId",
      nativeQuery = true)
  List<DriverJakartaEntity> findAllByMatchCountAndQWeekId(
      @Param("matchCount") final Integer matchCount, @Param("qWeekId") final Long qWeekId);

  DriverJakartaEntity findByTaxNumber(final Long taxNumber);
}
