package ee.qrent.billing.bolt.persistence.repository.spring;

import ee.qrent.billing.bolt.persistence.entity.jakarta.BoltStatisticsJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoltStatisticsSpringDataRepository
    extends JpaRepository<BoltStatisticsJakartaEntity, Long> {}
