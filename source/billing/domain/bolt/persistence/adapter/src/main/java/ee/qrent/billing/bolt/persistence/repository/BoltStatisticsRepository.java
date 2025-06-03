package ee.qrent.billing.bolt.persistence.repository;

import ee.qrent.billing.bolt.persistence.entity.jakarta.BoltStatisticsJakartaEntity;
import java.util.List;

public interface BoltStatisticsRepository {
  List<BoltStatisticsJakartaEntity> findAll();

  BoltStatisticsJakartaEntity save(final BoltStatisticsJakartaEntity entity);

  BoltStatisticsJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);
}
