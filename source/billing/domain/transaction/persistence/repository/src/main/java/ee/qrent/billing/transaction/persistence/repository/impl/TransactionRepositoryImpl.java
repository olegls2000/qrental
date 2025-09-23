package ee.qrent.billing.transaction.persistence.repository.impl;

import ee.qrent.billing.transaction.persistence.repository.TransactionRepository;
import ee.qrent.billing.transaction.persistence.entity.jakarta.TransactionJakartaEntity;
import ee.qrent.billing.transaction.persistence.repository.spring.TransactionSpringDataRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
  public List<TransactionJakartaEntity> findAllByBetweenDates(
      final LocalDate dateStart, final LocalDate dateEnd) {

    return springDataRepository.findAllByDateBetween(dateStart, dateEnd);
  }

  @Override
  public List<TransactionJakartaEntity> findAllByDriverIdAndBetweenDates(
      final Long driverId, final LocalDate dateStart, final LocalDate dateEnd) {

    return springDataRepository.findAllByDriverIdAndBetweenDates(driverId, dateStart, dateEnd);
  }

  @Override
  public List<TransactionJakartaEntity> findAllByDriverIdAndBetweenDatesAndNonFee(
      final Long driverId, final LocalDate dateStart, final LocalDate dateEnd) {

    return springDataRepository.findAllByDriverIdAndBetweenDatesAndNonFee(
        driverId, dateStart, dateEnd);
  }

  @Override
  public List<TransactionJakartaEntity> findAllByDriverIdAndBetweenDatesAndKindCodes(
      final Long driverId,
      final LocalDate dateStart,
      final LocalDate dateEnd,
      final Set<String>  kindCodes) {

    return springDataRepository.findAllByDriverIdAndBetweenDatesAndKindCodes(
        driverId, dateStart, dateEnd, kindCodes);
  }

  @Override
  public List<TransactionJakartaEntity> findAllByDriverIdAndBetweenDatesAndTypeCodes(
      final Long driverId,
      final LocalDate dateStart,
      final LocalDate dateEnd,
      final Set<String> typeCodes) {

    return springDataRepository.findAllByDriverIdAndBetweenDatesAndTypeCodes(
        driverId, dateStart, dateEnd, typeCodes);
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
  public List<TransactionJakartaEntity> findAllByDriverIdAndBetweenDatesAndFee(
      final Long driverId, final LocalDate dateStart, final LocalDate dateEnd) {

    return springDataRepository.findAllByDriverIdAndBetweenDatesAndFee(driverId, dateStart, dateEnd);
  }

  @Override
  public List<TransactionJakartaEntity> findByIds(List<Long> ids) {

    return springDataRepository.findAllByIdIn(ids);
  }
}
