package ee.qrent.billing.bonus.persistence.repository;

import ee.qrent.billing.bonus.persistence.entity.jakarta.ObligationJakartaEntity;
import java.util.List;

public interface ObligationRepository {
  List<ObligationJakartaEntity> findAll();

  ObligationJakartaEntity save(final ObligationJakartaEntity entity);

  ObligationJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);

  ObligationJakartaEntity findByDriverIdAndByQWeekId(final Long driverId, final Long qWekId);

  List<ObligationJakartaEntity> findByIds(final List<Long> ids);

  List<ObligationJakartaEntity> findByCalculationId(final Long id);
}
