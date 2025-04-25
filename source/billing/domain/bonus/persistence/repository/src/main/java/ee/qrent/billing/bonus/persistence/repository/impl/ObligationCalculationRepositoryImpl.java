package ee.qrent.billing.bonus.persistence.repository.impl;

import ee.qrent.billing.bonus.persistence.repository.ObligationCalculationRepository;
import ee.qrent.billing.bonus.persistence.entity.jakarta.ObligationCalculationJakartaEntity;
import ee.qrent.billing.bonus.persistence.repository.spring.ObligationCalculationSpringDataRepository;
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
