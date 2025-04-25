package ee.qrent.billing.insurance.repository.spring;

import ee.qrent.billing.insurance.persistence.entity.jakarta.InsuranceCaseJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InsuranceCaseSpringDataRepository
    extends JpaRepository<InsuranceCaseJakartaEntity, Long> {
  @Query(
      value =
          "SELECT ic.* FROM insurance_case ic "
              + "LEFT JOIN q_week case_start_week ON case_start_week.id = ic.start_q_week_id "
              + "WHERE "
              + "ic.active = true "
              + "AND ic.driver_id = :driverId "
              + "AND (case_start_week.year < (SELECT year FROM q_week WHERE id = :qWeekId) "
              + "OR "
              + "(case_start_week.year = (SELECT year FROM q_week WHERE id = :qWeekId) AND case_start_week.number <= (SELECT number FROM q_week WHERE id = :qWeekId)))",
      nativeQuery = true)
  List<InsuranceCaseJakartaEntity> findAllByActiveIsTrueAndDriverIdAndQWeekId(
      final @Param("driverId") Long driverId, final @Param("qWeekId") Long qWeekId);

  @Query(
      value =
          "SELECT ic.* FROM insurance_case ic "
              + "LEFT JOIN q_week case_start_week on case_start_week.id = ic.start_q_week_id "
              + "WHERE "
              + "ic.active = true "
              + "AND (case_start_week.year < (SELECT year FROM q_week WHERE id = :qWeekId) "
              + "OR "
              + "(case_start_week.year = (SELECT year FROM q_week WHERE id = :qWeekId) AND case_start_week.number <= (SELECT number FROM q_week WHERE id = :qWeekId)))",
      nativeQuery = true)
  List<InsuranceCaseJakartaEntity> findAllByActiveIsTrueAndQWeekId(
      final @Param("qWeekId") Long qWeekId);

  @Query(value = "SELECT * FROM insurance_case ic WHERE ic.active is true", nativeQuery = true)
  List<InsuranceCaseJakartaEntity> findActive();

  @Query(value = "SELECT * FROM insurance_case ic WHERE ic.active is false", nativeQuery = true)
  List<InsuranceCaseJakartaEntity> findClosed();

  @Query(
      value = "SELECT count(*) FROM insurance_case ic WHERE ic.active is true",
      nativeQuery = true)
  Long findCountActive();

  @Query(
      value = "SELECT count(*) FROM insurance_case ic WHERE ic.active is false",
      nativeQuery = true)
  Long findCountClosed();

  @Query(
      value =
          "SELECT * FROM insurance_case ic WHERE ic.active is true AND ic.driver_id = :driverId",
      nativeQuery = true)
  List<InsuranceCaseJakartaEntity> findActiveByDriverId(final @Param("driverId") Long driverId);

  @Query(
      value =
          "SELECT icbtx.transaction_id FROM insurance_case_balance_x_transaction icbtx "
              + "LEFT JOIN insurance_case_balance icb ON icbtx.insurance_case_balance_id = icb.id "
              + "WHERE icb.insurance_case_id = :insuranceCaseId",
      nativeQuery = true)
  List<Long> findPaymentTransactionIdsByInsuranceCaseId(
      final @Param("insuranceCaseId") Long insuranceCaseId);
}
