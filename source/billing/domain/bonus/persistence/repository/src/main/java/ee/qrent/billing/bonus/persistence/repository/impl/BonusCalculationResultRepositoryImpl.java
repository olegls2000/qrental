package ee.qrent.billing.bonus.persistence.repository.impl;

import ee.qrent.billing.bonus.persistence.repository.BonusCalculationResultRepository;
import ee.qrent.billing.bonus.persistence.entity.jakarta.BonusCalculationResultJakartaEntity;
import ee.qrent.billing.bonus.persistence.repository.spring.BonusCalculationResultSpringDataRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BonusCalculationResultRepositoryImpl
    implements BonusCalculationResultRepository {

  private final BonusCalculationResultSpringDataRepository springDataRepository;

  @Override
  public BonusCalculationResultJakartaEntity save(
      final BonusCalculationResultJakartaEntity entity) {
    return springDataRepository.save(entity);
  }
}
