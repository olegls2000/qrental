package ee.qrent.billing.bonus.persistence.repository.impl;

import ee.qrent.billing.bonus.persistence.repository.ObligationRepository;
import ee.qrent.billing.bonus.persistence.entity.jakarta.ObligationJakartaEntity;
import ee.qrent.billing.bonus.persistence.repository.spring.ObligationSpringDataRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObligationRepositoryImpl implements ObligationRepository {

  private final ObligationSpringDataRepository springDataRepository;

  @Override
  public List<ObligationJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public ObligationJakartaEntity save(final ObligationJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public ObligationJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }

  @Override
  public ObligationJakartaEntity findByDriverIdAndByQWeekId(
      final Long driverId, final Long qWekId) {
    return springDataRepository.findOneByDriverIdAndQWeekId(driverId, qWekId);
  }

  @Override
  public List<ObligationJakartaEntity> findByIds(List<Long> ids) {
    return springDataRepository.findAllByIdIn(ids);
  }

  @Override
  public List<ObligationJakartaEntity> findByCalculationId(final Long calculationId) {
    return springDataRepository.findByCalculationId(calculationId);
  }
}
