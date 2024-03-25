package ee.qrental.bonus.adapter.repository;

import ee.qrental.bonus.entity.jakarta.BonusCalculationResultJakartaEntity;

public interface BonusCalculationResultRepository {
  BonusCalculationResultJakartaEntity save(final BonusCalculationResultJakartaEntity entity);
}
