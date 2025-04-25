package ee.qrent.billing.transaction.persistence.repository.impl.balance;

import ee.qrent.billing.transaction.persistence.repository.balance.BalanceCalculationResultRepository;
import ee.qrent.billing.transaction.persistence.entity.jakarta.balance.BalanceCalculationResultJakartaEntity;
import ee.qrent.billing.transaction.persistence.repository.spring.balance.BalanceCalculationResultSpringDataRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationResultRepositoryImpl implements BalanceCalculationResultRepository {

  private final BalanceCalculationResultSpringDataRepository springDataRepository;

  @Override
  public BalanceCalculationResultJakartaEntity save(
      final BalanceCalculationResultJakartaEntity entity) {
    return springDataRepository.save(entity);
  }
}
