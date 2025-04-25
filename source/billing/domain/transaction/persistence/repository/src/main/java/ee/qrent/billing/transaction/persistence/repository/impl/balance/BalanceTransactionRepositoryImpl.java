package ee.qrent.billing.transaction.persistence.repository.impl.balance;

import ee.qrent.billing.transaction.persistence.repository.balance.BalanceTransactionRepository;
import ee.qrent.billing.transaction.persistence.repository.spring.balance.BalanceTransactionSpringDataRepository;
import ee.qrent.billing.transaction.persistence.entity.jakarta.balance.BalanceTransactionJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceTransactionRepositoryImpl implements BalanceTransactionRepository {

  private final BalanceTransactionSpringDataRepository springDataRepository;

  @Override
  public BalanceTransactionJakartaEntity save(final BalanceTransactionJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public boolean isTransactionCalculatedInBalance(final Long transactionId) {
    return springDataRepository.existsTransactionBalanceJakartaEntityByTransactionId(transactionId);
  }
}
