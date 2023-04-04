package ee.qrental.transaction.repository.impl;

import ee.qrental.transaction.adapter.repository.TransactionRepository;
import ee.qrental.transaction.entity.jakarta.TransactionJakartaEntity;
import ee.qrental.transaction.repository.spring.TransactionSpringDataRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

  private final TransactionSpringDataRepository springDataRepository;

  @Override
  public List<TransactionJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public TransactionJakartaEntity save(final TransactionJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public List<TransactionJakartaEntity> findByDriverId(final Long driverId) {
    return springDataRepository.findByDriverId(driverId);
  }

  @Override
  public List<TransactionJakartaEntity> findAllByDateBetween(
      LocalDate dateStart, LocalDate dateEnd) {
    return springDataRepository.findAllByDateBetween(dateStart, dateEnd);
  }

  @Override
  public List<TransactionJakartaEntity> findAllByDateBetweenAndDriverId(
      LocalDate dateStart, LocalDate dateEnd, Long driverId) {
    return springDataRepository.findAllByDateBetweenAndDriverId(dateStart, dateEnd, driverId);
  }

  @Override
  public List<TransactionJakartaEntity> findAllByCalculationId(final Long calculationId) {
    return springDataRepository.findAllByCalculationId(calculationId);
  }

  @Override
  public TransactionJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }
}
