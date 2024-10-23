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
      final LocalDate dateStart, final LocalDate dateEnd) {
    return springDataRepository.findAllByDateBetween(dateStart, dateEnd);
  }

  @Override
  public List<TransactionJakartaEntity> findAllByDateBetweenAndDriverId(
      final LocalDate dateStart, final LocalDate dateEnd, Long driverId) {
    return springDataRepository.findAllByDateBetweenAndDriverId(dateStart, dateEnd, driverId);
  }

  @Override
  public List<TransactionJakartaEntity> findAllNonFeeByDateBetweenAndDriverId(
      final LocalDate dateStart, final LocalDate dateEnd, final Long driverId) {
    return springDataRepository.findAllNonFeeByDateBetweenAndDriverId(dateStart, dateEnd, driverId);
  }

  @Override
  public List<TransactionJakartaEntity> findAllByRentCalculationId(final Long rentCalculationId) {
    return springDataRepository.findAllByRentCalculationId(rentCalculationId);
  }

  @Override
  public List<TransactionJakartaEntity> findAllByInsuranceCalculationId(
      final Long insuranceCalculationId) {
    return springDataRepository.findAllByInsuranceCalculationId(insuranceCalculationId);
  }

  @Override
  public List<TransactionJakartaEntity> findAllByInsuranceCaseId(final Long insuranceCaseId) {
    return springDataRepository.findAllByInsuranceCaseId(insuranceCaseId);
  }

  @Override
  public List<TransactionJakartaEntity> findAllByBonusCalculationId(final Long bonusCalculationId) {
    return springDataRepository.findAllByBonusCalculationId(bonusCalculationId);
  }

  @Override
  public TransactionJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }

  @Override
  public List<TransactionJakartaEntity> findAllFeeByDateBetweenAndDriverId(
      LocalDate dateStart, LocalDate dateEnd, Long driverId) {
    return springDataRepository.findAllFeeByDateBetweenAndDriverId(dateStart, dateEnd, driverId);
  }

  @Override
  public List<TransactionJakartaEntity> findByIds(List<Long> ids) {
    return springDataRepository.findAllByIdIn(ids);
  }

  @Override
  public List<TransactionJakartaEntity> findAllByDriverIdAndKindIdAndBetweenDays(
      final Long driverId,
      final List<Long> kindIds,
      final LocalDate dateStart,
      final LocalDate dateEnd) {

    return springDataRepository.findAllByDriverIdAndKindIdAndBetweenDays(
        driverId, kindIds, dateStart, dateEnd);
  }
}
