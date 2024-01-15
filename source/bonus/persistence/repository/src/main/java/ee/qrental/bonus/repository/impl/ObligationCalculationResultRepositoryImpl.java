package ee.qrental.bonus.repository.impl;

import ee.qrental.bonus.adapter.repository.ObligationCalculationResultRepository;
import ee.qrental.bonus.entity.jakarta.ObligationCalculationResultJakartaEntity;
import ee.qrental.bonus.repository.spring.ObligationCalculationResultSpringDataRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObligationCalculationResultRepositoryImpl
    implements ObligationCalculationResultRepository {

  private final ObligationCalculationResultSpringDataRepository springDataRepository;

  @Override
  public ObligationCalculationResultJakartaEntity save(
      final ObligationCalculationResultJakartaEntity entity) {
    return springDataRepository.save(entity);
  }
}
