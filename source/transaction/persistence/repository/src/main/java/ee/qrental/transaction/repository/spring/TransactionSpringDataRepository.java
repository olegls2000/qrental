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
  List<TransactionJakartaEntity> findAllByRentCalculationId(
      @Param("rentCalculationId") final Long rentCalculationId);

  @Query(
      value =
          "SELECT * FROM transaction tx "
              + "WHERE tx.id  in ("
              + "select icbxt.transaction_id from insurance_case_balance_x_transaction icbxt "
              + "where icbxt.insurance_case_balance_id in "
              + "(select icb.id from insurance_case_balance icb where icb.insurance_calculation_id = :insuranceCalculationId) "
              + ")",
      nativeQuery = true)
  List<TransactionJakartaEntity> findAllByInsuranceCalculationId(
      @Param("insuranceCalculationId") final Long insuranceCalculationId);

  @Query(
      value =
          "SELECT * FROM transaction tx "
              + "WHERE tx.id  in ("
              + "select icbxt.transaction_id from insurance_case_balance_x_transaction icbxt "
              + "where icbxt.insurance_case_balance_id in "
              + "(select icb.id from insurance_case_balance icb where icb.insurance_case_id = :insuranceCaseId) "
              + ")",
      nativeQuery = true)
  List<TransactionJakartaEntity> findAllByInsuranceCaseId(
      @Param("insuranceCaseId") final Long insuranceCaseId);

  @Query(
      value =
          "SELECT * FROM transaction tx "
              + "WHERE tx.id  in ("
              + "select clr.transaction_id from bonus_calculation_result clr "
              + "where clr.bonus_calculation_id = :bonusCalculationId)",
      nativeQuery = true)
  List<TransactionJakartaEntity> findAllByBonusCalculationId(
      @Param("bonusCalculationId") final Long bonusCalculationId);

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

  @Query(
      value =
          "SELECT * FROM transaction tx "
              + "WHERE tx.transaction_type_id in ("
              + "select txt.id from transaction_type txt where txt.transaction_kind_id in (:kindIds)) "
              + "and tx.driver_id = :driverId "
              + "and tx.date >= :dateStart and tx.date <= :dateEnd",
      nativeQuery = true)
  List<TransactionJakartaEntity> findAllByDriverIdAndKindIdAndBetweenDays(
      @Param("driverId") Long driverId,
      @Param("kindIds") List<Long> kindIds,
      @Param("dateStart") LocalDate dateStart,
      @Param("dateEnd") LocalDate dateEnd);
}
