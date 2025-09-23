package ee.qrent.billing.transaction.persistence.repository;

import ee.qrent.billing.transaction.persistence.entity.jakarta.TransactionJakartaEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TransactionRepository {
  List<TransactionJakartaEntity> findAll();

  TransactionJakartaEntity getReferenceById(Long id);

  List<TransactionJakartaEntity> findByIds(final List<Long> ids);

  List<TransactionJakartaEntity> findAllByBetweenDates(
      final LocalDate dateStart, final LocalDate dateEnd);

  List<TransactionJakartaEntity> findByDriverId(final Long driverId);

  List<TransactionJakartaEntity> findAllByDriverIdAndBetweenDates(
      final Long driverId, final LocalDate dateStart, final LocalDate dateEnd);

  List<TransactionJakartaEntity> findAllByDriverIdAndBetweenDatesAndNonFee(
      final Long driverId, final LocalDate dateStart, final LocalDate dateEnd);

  List<TransactionJakartaEntity> findAllByDriverIdAndBetweenDatesAndFee(
      final Long driverId, final LocalDate dateStart, final LocalDate dateEnd);

  List<TransactionJakartaEntity> findAllByDriverIdAndBetweenDatesAndKindIds(
      final Long driverId,
      final LocalDate dateStart,
      final LocalDate dateEnd,
      final List<Long> kindIds);

  List<TransactionJakartaEntity> findAllByDriverIdAndBetweenDatesAndTypeCodes(
      final Long driverId,
      final LocalDate dateStart,
      final LocalDate dateEnd,
      final Set<String> typeCodes);

  List<TransactionJakartaEntity> findAllByRentCalculationId(final Long rentCalculationId);

  List<TransactionJakartaEntity> findAllByInsuranceCalculationId(final Long insuranceCalculationId);

  List<TransactionJakartaEntity> findAllByInsuranceCaseId(final Long insuranceCaseId);

  List<TransactionJakartaEntity> findAllByBonusCalculationId(final Long bonusCalculationId);

  TransactionJakartaEntity save(final TransactionJakartaEntity entity);

  void deleteById(final Long id);
}
