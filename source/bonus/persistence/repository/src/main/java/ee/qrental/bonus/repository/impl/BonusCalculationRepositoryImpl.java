package ee.qrental.bonus.repository.impl;

import ee.qrental.bonus.adapter.repository.BonusCalculationRepository;
import ee.qrental.bonus.adapter.repository.ObligationCalculationRepository;
import ee.qrental.bonus.entity.jakarta.BonusCalculationJakartaEntity;
import ee.qrental.bonus.entity.jakarta.ObligationCalculationJakartaEntity;
import ee.qrental.bonus.repository.spring.BonusCalculationSpringDataRepository;
import ee.qrental.bonus.repository.spring.ObligationCalculationSpringDataRepository;
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
