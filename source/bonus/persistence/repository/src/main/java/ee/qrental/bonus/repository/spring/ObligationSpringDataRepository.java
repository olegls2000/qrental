package ee.qrental.bonus.repository.spring;

import ee.qrental.bonus.entity.jakarta.ObligationJakartaEntity;
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
}
