package ee.qrental.bonus.repository.impl;

import ee.qrental.bonus.adapter.repository.BonusCalculationResultRepository;
import ee.qrental.bonus.entity.jakarta.BonusCalculationResultJakartaEntity;
import ee.qrental.bonus.repository.spring.BonusCalculationResultSpringDataRepository;
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
