package ee.qrental.bonus.repository.impl;

import ee.qrental.bonus.adapter.repository.ObligationCalculationRepository;
import ee.qrental.bonus.entity.jakarta.ObligationCalculationJakartaEntity;
import ee.qrental.bonus.repository.spring.ObligationCalculationSpringDataRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObligationCalculationRepositoryImpl implements ObligationCalculationRepository {

  private final ObligationCalculationSpringDataRepository springDataRepository;

  @Override
  public ObligationCalculationJakartaEntity save(final ObligationCalculationJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public List<ObligationCalculationJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public ObligationCalculationJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public Long getLastCalculationQWeekId() {
    return springDataRepository.getLastCalculationQWeekId();
  }
}
