package ee.qrental.balance.adapter.repository;

import ee.qrental.invoice.entity.jakarta.BalanceTransactionJakartaEntity;

public interface BalanceTransactionRepository {
  BalanceTransactionJakartaEntity save(final BalanceTransactionJakartaEntity entity);
}
