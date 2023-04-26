package ee.qrental.balance.adapter.repository;

import ee.qrental.invoice.entity.jakarta.BalanceCalculationResultJakartaEntity;

public interface BalanceCalculationResultRepository {
  BalanceCalculationResultJakartaEntity save(final BalanceCalculationResultJakartaEntity entity);
}
