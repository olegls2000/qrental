package ee.qrental.transaction.adapter.repository;

import ee.qrental.transaction.entity.jakarta.TransactionJakartaEntity;
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

  List<TransactionJakartaEntity> findAllByCalculationId(final Long calculationId);

  TransactionJakartaEntity getReferenceById(Long id);

  TransactionJakartaEntity save(final TransactionJakartaEntity entity);

  void deleteById(final Long id);

  List<TransactionJakartaEntity> findAllFeeByDateBetweenAndDriverId(
          final LocalDate dateStart,
          final LocalDate dateEnd,
          final Long driverId);
}
