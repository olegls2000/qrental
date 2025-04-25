package ee.qrent.billing.bonus.persistence.repository.impl;

import ee.qrent.billing.bonus.persistence.repository.ObligationCalculationResultRepository;
import ee.qrent.billing.bonus.persistence.entity.jakarta.ObligationCalculationResultJakartaEntity;
import ee.qrent.billing.bonus.persistence.repository.spring.ObligationCalculationResultSpringDataRepository;
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
