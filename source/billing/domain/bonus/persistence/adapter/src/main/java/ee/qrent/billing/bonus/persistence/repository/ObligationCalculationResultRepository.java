package ee.qrent.billing.bonus.persistence.repository;

import ee.qrent.billing.bonus.persistence.entity.jakarta.ObligationCalculationResultJakartaEntity;

public interface ObligationCalculationResultRepository {
  ObligationCalculationResultJakartaEntity save(
      final ObligationCalculationResultJakartaEntity entity);
}
