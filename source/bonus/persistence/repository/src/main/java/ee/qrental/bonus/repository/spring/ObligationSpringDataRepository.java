package ee.qrental.bonus.repository.spring;

import ee.qrental.bonus.entity.jakarta.ObligationJakartaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObligationSpringDataRepository
    extends JpaRepository<ObligationJakartaEntity, Long> {
  ObligationJakartaEntity findOneByDriverIdAndQWeekId(final Long driverId, final Long qWeekId);
  List<ObligationJakartaEntity> findAllByIdIn(final List<Long> ids);
}
