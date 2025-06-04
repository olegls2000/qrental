package ee.qrent.billing.bolt.persistence.repository.spring;

import ee.qrent.billing.bolt.persistence.entity.jakarta.BoltOrdersCountJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoltOrdersCountSpringDataRepository
    extends JpaRepository<BoltOrdersCountJakartaEntity, Long> {

  BoltOrdersCountJakartaEntity findOneByDriverIdAndQWeekId(final Long driverId, final Long weekId);
}
