package ee.qrent.billing.bonus.persistence.repository.impl;

import ee.qrent.billing.bonus.persistence.repository.BonusCalculationRepository;
import ee.qrent.billing.bonus.persistence.entity.jakarta.BonusCalculationJakartaEntity;
import ee.qrent.billing.bonus.persistence.repository.spring.BonusCalculationSpringDataRepository;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BonusCalculationRepositoryImpl implements BonusCalculationRepository {

  private final BonusCalculationSpringDataRepository springDataRepository;

  @Override
  public BonusCalculationJakartaEntity save(final BonusCalculationJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public List<BonusCalculationJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public BonusCalculationJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public Long getLastCalculationQWeekId() {
    return springDataRepository.getLastCalculationQWeekId();
  }
}
