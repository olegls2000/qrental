package ee.qrental.bonus.repository.spring;

import ee.qrental.bonus.entity.jakarta.BonusProgramJakartaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonusProgramSpringDataRepository
    extends JpaRepository<BonusProgramJakartaEntity, Long> {
  
  List<BonusProgramJakartaEntity> findAllByActive(final boolean active);
}
