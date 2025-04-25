package ee.qrent.billing.bonus.persistence.repository.spring;

import ee.qrent.billing.bonus.persistence.entity.jakarta.ObligationJakartaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ObligationSpringDataRepository
    extends JpaRepository<ObligationJakartaEntity, Long> {

  @Query(
      value =
          "select ob.* from obligation ob where ob.driver_id =:driverId and ob.q_week_id =:qWeekId",
      nativeQuery = true)
  ObligationJakartaEntity findOneByDriverIdAndQWeekId(
      @Param("driverId") final Long driverId, @Param("qWeekId") final Long qWeekId);
  List<ObligationJakartaEntity> findAllByIdIn(final List<Long> ids);

  @Query(
      value =
          "select ob.* from obligation ob "
              + " LEFT JOIN obligation_calculation_result ocr ON ob.id = ocr.obligation_id "
              + " where ocr.obligation_calculation_id =:calculationId",
      nativeQuery = true)
  List<ObligationJakartaEntity> findByCalculationId(
      @Param("calculationId") final Long calculationId);
}
