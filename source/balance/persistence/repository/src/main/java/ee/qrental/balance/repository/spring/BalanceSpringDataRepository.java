package ee.qrental.balance.repository.spring;

import ee.qrental.invoice.entity.jakarta.BalanceJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BalanceSpringDataRepository extends JpaRepository<BalanceJakartaEntity, Long> {

  BalanceJakartaEntity findByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber);

  @Query(
      value =
          "select bl.* from balance bl where bl.driver_id = :driverId "
              + "order by year desc, week_number desc limit 1",
      nativeQuery = true)
  BalanceJakartaEntity findLatestByDriverId(@Param("driverId") final Long driverId);
}
