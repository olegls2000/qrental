package ee.qrent.billing.bonus.persistence.repository;

import ee.qrent.billing.bonus.persistence.entity.jakarta.BonusProgramJakartaEntity;
import java.util.List;

public interface BonusProgramRepository {
  List<BonusProgramJakartaEntity> findAll();

  List<BonusProgramJakartaEntity> findAllByActive(final boolean active);

  BonusProgramJakartaEntity save(final BonusProgramJakartaEntity entity);

  BonusProgramJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);
}
