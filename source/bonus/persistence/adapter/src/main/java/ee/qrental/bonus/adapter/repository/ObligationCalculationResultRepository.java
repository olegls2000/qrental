package ee.qrental.bonus.adapter.repository;

import ee.qrental.bonus.entity.jakarta.ObligationCalculationResultJakartaEntity;

public interface ObligationCalculationResultRepository {
  ObligationCalculationResultJakartaEntity save(
      final ObligationCalculationResultJakartaEntity entity);
}
