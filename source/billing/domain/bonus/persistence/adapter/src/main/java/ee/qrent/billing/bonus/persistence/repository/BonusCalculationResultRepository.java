package ee.qrent.billing.bonus.persistence.repository;

import ee.qrent.billing.bonus.persistence.entity.jakarta.BonusCalculationResultJakartaEntity;

public interface BonusCalculationResultRepository {
  BonusCalculationResultJakartaEntity save(final BonusCalculationResultJakartaEntity entity);
}
