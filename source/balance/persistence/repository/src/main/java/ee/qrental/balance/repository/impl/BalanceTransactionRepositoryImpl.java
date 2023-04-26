package ee.qrental.balance.repository.impl;

import ee.qrental.balance.adapter.repository.BalanceTransactionRepository;
import ee.qrental.balance.repository.spring.BalanceTransactionSpringDataRepository;
import ee.qrental.invoice.entity.jakarta.BalanceTransactionJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceTransactionRepositoryImpl implements BalanceTransactionRepository {

  private final BalanceTransactionSpringDataRepository springDataRepository;

  @Override
  public BalanceTransactionJakartaEntity save(final BalanceTransactionJakartaEntity entity) {
    return springDataRepository.save(entity);
  }
}
