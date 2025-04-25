package ee.qrent.billing.bonus.persistence.repository.spring;

import ee.qrent.billing.bonus.persistence.entity.jakarta.BonusProgramJakartaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonusProgramSpringDataRepository
    extends JpaRepository<BonusProgramJakartaEntity, Long> {
  
  List<BonusProgramJakartaEntity> findAllByActive(final boolean active);
}
