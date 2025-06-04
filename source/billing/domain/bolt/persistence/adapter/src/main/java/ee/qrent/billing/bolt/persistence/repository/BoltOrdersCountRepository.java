package ee.qrent.billing.bolt.persistence.repository;

import ee.qrent.billing.bolt.persistence.entity.jakarta.BoltOrdersCountJakartaEntity;

public interface BoltOrdersCountRepository {

  BoltOrdersCountJakartaEntity save(final BoltOrdersCountJakartaEntity entity);

  BoltOrdersCountJakartaEntity getByDriverIdAndQWeekId(final Long driverId, final Long qWeekId);
}
