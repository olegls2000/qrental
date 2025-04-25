package ee.qrent.billing.transaction.persistence.repository;

import ee.qrent.billing.transaction.persistence.entity.jakarta.TransactionJakartaEntity;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository {

  List<TransactionJakartaEntity> findAll();

  List<TransactionJakartaEntity> findByDriverId(final Long driverId);

  List<TransactionJakartaEntity> findAllByDateBetween(
      final LocalDate dateStart, final LocalDate dateEnd);

  List<TransactionJakartaEntity> findAllByDateBetweenAndDriverId(
      final LocalDate dateStart, final LocalDate dateEnd, final Long driverId);

  List<TransactionJakartaEntity> findAllNonFeeByDateBetweenAndDriverId(
      final LocalDate dateStart, final LocalDate dateEnd, final Long driverId);

  List<TransactionJakartaEntity> findAllByRentCalculationId(final Long rentCalculationId);

  List<TransactionJakartaEntity> findAllByInsuranceCalculationId(final Long insuranceCalculationId);

  List<TransactionJakartaEntity> findAllByInsuranceCaseId(final Long insuranceCaseId);

  List<TransactionJakartaEntity> findAllByBonusCalculationId(final Long bonusCalculationId);

  TransactionJakartaEntity getReferenceById(Long id);

  TransactionJakartaEntity save(final TransactionJakartaEntity entity);

  void deleteById(final Long id);

  List<TransactionJakartaEntity> findAllFeeByDateBetweenAndDriverId(
      final LocalDate dateStart, final LocalDate dateEnd, final Long driverId);

  List<TransactionJakartaEntity> findByIds(final List<Long> ids);

  List<TransactionJakartaEntity> findAllByDriverIdAndKindIdAndBetweenDays(
      final Long driverId,
      final List<Long> kindIds,
      final LocalDate dateStart,
      final LocalDate dateEnd);
}
