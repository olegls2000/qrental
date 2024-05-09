package ee.qrental.transaction.repository.spring.balance;

import ee.qrental.transaction.entity.jakarta.balance.BalanceJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BalanceSpringDataRepository extends JpaRepository<BalanceJakartaEntity, Long> {

  @Query(
      value =
          "select bl.* from balance bl LEFT JOIN q_week qw on bl.q_week_id = qw.id "
              + " where bl.driver_id = :driverId and qw.year =:year and qw.number = :weekNumber",
      nativeQuery = true)
  BalanceJakartaEntity findByDriverIdAndYearAndWeekNumber(
      @Param("driverId") final Long driverId,
      @Param("year") final Integer year,
      @Param("weekNumber") final Integer weekNumber);

  @Query(
      value =
          "select bl.* from balance bl where bl.driver_id = :driverId and bl.q_week_id = :qWeekId and bl.derived = :derived",
      nativeQuery = true)
  BalanceJakartaEntity findByDriverIdAndQWeekIdAndDerived(
      @Param("driverId") final Long driverId,
      @Param("qWeekId") final Long qWeekId,
      @Param("derived") final boolean derived);

  @Query(
      value =
          "select bl.* from balance bl LEFT JOIN q_week qw on bl.q_week_id = qw.id "
              + "where bl.driver_id = :driverId and bl.derived = true "
              + "order by qw.year desc, qw.number desc limit 1",
      nativeQuery = true)
  BalanceJakartaEntity findLatestByDriverId(@Param("driverId") final Long driverId);

  @Query(
      value = "select count(bl.*) from balance bl where bl.driver_id = :driverId ",
      nativeQuery = true)
  Long getCountByDriverId(@Param("driverId") final Long driverId);

  @Query(
      value =
          "select bl.* from balance bl LEFT JOIN q_week qw on bl.q_week_id = qw.id "
              + "order by qw.year desc, qw.number desc limit 1",
      nativeQuery = true)
  BalanceJakartaEntity findLatest();

  @Query(
      value =
          "select bl.* from balance bl LEFT JOIN q_week qw on bl.q_week_id = qw.id "
              + "where bl.driver_id = :driverId "
              + "and qw.year <= :year "
              + "and qw.number <= :weekNumber "
              + "order by qw.year desc, qw.number desc limit 1",
      nativeQuery = true)
  BalanceJakartaEntity findLatestByDriverIdAndYearAndWeekNumber(
      @Param("driverId") final Long driverId,
      @Param("year") final Integer year,
      @Param("weekNumber") final Integer weekNumber);
}
