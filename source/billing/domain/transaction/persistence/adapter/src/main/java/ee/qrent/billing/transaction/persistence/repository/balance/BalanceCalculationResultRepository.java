package ee.qrent.billing.transaction.persistence.repository.balance;


import ee.qrent.billing.transaction.persistence.entity.jakarta.balance.BalanceCalculationResultJakartaEntity;

public interface BalanceCalculationResultRepository {
  BalanceCalculationResultJakartaEntity save(final BalanceCalculationResultJakartaEntity entity);
}
