package ee.qrental.transaction.adapter.repository.balance;


import ee.qrental.transaction.entity.jakarta.balance.BalanceCalculationResultJakartaEntity;

public interface BalanceCalculationResultRepository {
  BalanceCalculationResultJakartaEntity save(final BalanceCalculationResultJakartaEntity entity);
}
