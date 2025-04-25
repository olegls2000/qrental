package ee.qrent.billing.transaction.persistence.repository.balance;


import ee.qrent.billing.transaction.persistence.entity.jakarta.balance.BalanceTransactionJakartaEntity;

public interface BalanceTransactionRepository {
  BalanceTransactionJakartaEntity save(final BalanceTransactionJakartaEntity entity);
  boolean isTransactionCalculatedInBalance(final Long transactionId);
}
