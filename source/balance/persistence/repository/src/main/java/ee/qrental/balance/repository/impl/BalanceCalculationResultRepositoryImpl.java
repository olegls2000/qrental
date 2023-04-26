package ee.qrental.balance.repository.impl;

import ee.qrental.balance.adapter.repository.BalanceCalculationResultRepository;
import ee.qrental.balance.repository.spring.BalanceCalculationResultSpringDataRepository;
import ee.qrental.invoice.entity.jakarta.BalanceCalculationResultJakartaEntity;
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
