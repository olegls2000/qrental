package ee.qrent.billing.contract.persistence.repository.impl;

import ee.qrent.billing.contract.persistence.repository.ContractRepository;
import ee.qrent.billing.contract.persistence.entity.jakarta.ContractJakartaEntity;
import ee.qrent.billing.contract.persistence.repository.spring.ContractSpringDataRepository;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContractRepositoryImpl implements ContractRepository {

  private final ContractSpringDataRepository springDataRepository;

  @Override
  public List<ContractJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public ContractJakartaEntity save(final ContractJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public ContractJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }

  @Override
  public ContractJakartaEntity getByNumber(final String number) {
    return springDataRepository.findByNumber(number);
  }

  @Override
  public ContractJakartaEntity findLatestByDriverId(final Long driverId) {
    return springDataRepository.findLatestByDriverId(driverId);
  }

  @Override
  public List<ContractJakartaEntity> findActiveByDate(final LocalDate date) {
    return springDataRepository.findActiveByDate(date);
  }

  @Override
  public ContractJakartaEntity findActiveByDateAndDriverId(
      final LocalDate date, final Long driverId) {
    return springDataRepository.findActiveByDateAndDriverId(date, driverId);
  }

  @Override
  public Long findCountActiveByDate(final LocalDate date) {
    return springDataRepository.findCountActiveByDate(date);
  }

  @Override
  public List<ContractJakartaEntity> findClosedByDate(final LocalDate date) {
    return springDataRepository.findClosedByDate(date);
  }

  @Override
  public Long findCountClosedByDate(final LocalDate date) {
    return springDataRepository.findCountClosedByDate(date);
  }

  @Override
  public List<ContractJakartaEntity> findAllByDriverId(final Long driverId) {
    return springDataRepository.findAllByDriverId(driverId);
  }
}
