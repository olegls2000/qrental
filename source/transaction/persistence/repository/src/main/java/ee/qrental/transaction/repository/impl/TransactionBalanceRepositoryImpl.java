package ee.qrental.transaction.repository.impl;

import ee.qrental.transaction.adapter.repository.TransactionBalanceRepository;
import ee.qrental.transaction.repository.spring.TransactionBalanceSpringDataRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionBalanceRepositoryImpl implements TransactionBalanceRepository {

  private final TransactionBalanceSpringDataRepository springDataRepository;

  @Override
  public boolean isTransactionCalculatedInBalance(final Long transactionId) {
    return springDataRepository.existsTransactionBalanceJakartaEntityByTransactionId(transactionId);
  }
}
