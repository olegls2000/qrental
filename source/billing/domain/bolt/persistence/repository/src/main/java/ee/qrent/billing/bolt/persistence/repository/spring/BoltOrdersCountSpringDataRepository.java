package ee.qrent.billing.bolt.persistence.repository.spring;

import ee.qrent.billing.bolt.persistence.entity.jakarta.BoltOrdersCountJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoltOrdersCountSpringDataRepository
    extends JpaRepository<BoltOrdersCountJakartaEntity, Long> {

  @Query(
      value =
          "select boc.* from bolt_orders_count boc where boc.driver_id =:driverId and boc.q_week_id =:qWeekId",
      nativeQuery = true)
  BoltOrdersCountJakartaEntity findOneByDriverIdAndQWeekId(final Long driverId, final Long qWeekId);

  List<BoltOrdersCountJakartaEntity> findAllByYearAndMonth(final Integer year, final Integer month);
}
