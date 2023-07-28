package ee.qrental.transaction.adapter.repository.balance;


import ee.qrental.transaction.entity.jakarta.balance.BalanceTransactionJakartaEntity;

public interface BalanceTransactionRepository {
  BalanceTransactionJakartaEntity save(final BalanceTransactionJakartaEntity entity);
  boolean isTransactionCalculatedInBalance(final Long transactionId);
}
