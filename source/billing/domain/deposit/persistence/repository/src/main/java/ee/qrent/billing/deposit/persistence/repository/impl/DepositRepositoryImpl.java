package ee.qrent.billing.deposit.persistence.repository.impl;

import ee.qrent.billing.deposit.persistence.entity.jakarta.DepositJakartaEntity;
import ee.qrent.billing.deposit.persistence.repository.DepositRepository;
import ee.qrent.billing.deposit.persistence.repository.spring.DepositSpringDataRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class DepositRepositoryImpl implements DepositRepository {

  private final DepositSpringDataRepository springDataRepository;

  @Override
  public List<DepositJakartaEntity> findAll() {

    return springDataRepository.findAll();
  }

  @Override
  public DepositJakartaEntity save(final DepositJakartaEntity entity) {

    return springDataRepository.save(entity);
  }

  @Override
  public DepositJakartaEntity getReferenceById(final Long id) {

    return springDataRepository.getReferenceById(id);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }

  @Override
  public List<DepositJakartaEntity> findAllByDriverId(final Long driverId) {

    return springDataRepository.findAllByDriverId(driverId);
  }
}
