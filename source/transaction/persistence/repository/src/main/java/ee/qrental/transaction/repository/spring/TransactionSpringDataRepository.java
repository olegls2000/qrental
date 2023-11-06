package ee.qrental.transaction.repository.spring;

import ee.qrental.transaction.entity.jakarta.TransactionJakartaEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionSpringDataRepository
    extends JpaRepository<TransactionJakartaEntity, Long> {

  List<TransactionJakartaEntity> findByDriverId(Long driverId);

  List<TransactionJakartaEntity> findAllByDateBetween(LocalDate dateStart, LocalDate dateEnd);

  @Query(
          value =
                  "SELECT * FROM transaction tx "
                          + "WHERE tx.driver_id = :driverId "
                          + "and tx.date >= :dateStart and tx.date <= :dateEnd",
          nativeQuery = true)
  List<TransactionJakartaEntity> findAllByDateBetweenAndDriverId(
          @Param("dateStart") LocalDate dateStart,
          @Param("dateEnd") LocalDate dateEnd,
          @Param("driverId") Long driverId);

  @Query(
      value =
          "SELECT * FROM transaction tx "
              + "WHERE tx.id  in ("
              + "select clr.transaction_id from rent_calculation_result clr "
              + "where clr.rent_calculation_id = :rentCalculationId)",
      nativeQuery = true)
  List<TransactionJakartaEntity> findAllByRentCalculationId(@Param("rentCalculationId") Long rentCalculationId);

  @Query(
          value =
                  "SELECT * FROM transaction tx "
                          + "WHERE tx.transaction_type_id  in ("
                          + "select txt.id from transaction_type txt "
                          + "where txt.name <> 'fee debt' and txt.name <> 'fee replenish') "
                          + "and tx.driver_id = :driverId "
                          + "and tx.date >= :dateStart and tx.date <= :dateEnd",
          nativeQuery = true)
  List<TransactionJakartaEntity> findAllNonFeeByDateBetweenAndDriverId(
          @Param("dateStart") LocalDate dateStart,
          @Param("dateEnd") LocalDate dateEnd,
          @Param("driverId") Long driverId);

  @Query(
          value =
                  "SELECT * FROM transaction tx "
                          + "WHERE tx.transaction_type_id  in ("
                          + "select txt.id from transaction_type txt "
                          + "where txt.name in ('fee debt', 'fee replenish')) "
                          + "and tx.driver_id = :driverId "
                          + "and tx.date >= :dateStart and tx.date <= :dateEnd",
          nativeQuery = true)
  List<TransactionJakartaEntity> findAllFeeByDateBetweenAndDriverId(
          @Param("dateStart") LocalDate dateStart,
          @Param("dateEnd") LocalDate dateEnd,
          @Param("driverId") Long driverId);


  List<TransactionJakartaEntity> findAllByIdIn(List<Long> ids);
}