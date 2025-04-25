package ee.qrent.billing.driver.persistence.repository.impl;

import ee.qrent.billing.driver.persistence.repository.DriverRepository;
import ee.qrent.billing.driver.persistence.entity.jakarta.DriverJakartaEntity;
import ee.qrent.billing.driver.persistence.repository.spring.DriverSpringDataRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DriverRepositoryImpl implements DriverRepository {

  private final DriverSpringDataRepository springDataRepository;

  @Override
  public List<DriverJakartaEntity> findAll() {

    return springDataRepository.findAll();
  }

  @Override
  public DriverJakartaEntity save(final DriverJakartaEntity entity) {

    return springDataRepository.save(entity);
  }

  @Override
  public DriverJakartaEntity findById(final Long id) {

    return springDataRepository.getReferenceById(id);
  }

  @Override
  public DriverJakartaEntity findByTaxNumber(final Long taxNumber) {

    return springDataRepository.findByTaxNumber(taxNumber);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }

  @Override
  public List<DriverJakartaEntity> findAllByMatchCountAndQWeekId(
      final Integer matchCount, final Long qWeekId) {

    return springDataRepository.findAllByMatchCountAndQWeekId(matchCount, qWeekId);
  }
}
