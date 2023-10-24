package ee.qrental.transaction.repository.impl.balance;

import ee.qrental.transaction.adapter.repository.balance.BalanceCalculationResultRepository;
import ee.qrental.transaction.entity.jakarta.balance.BalanceCalculationResultJakartaEntity;
import ee.qrental.transaction.repository.spring.balance.BalanceCalculationResultSpringDataRepository;
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
