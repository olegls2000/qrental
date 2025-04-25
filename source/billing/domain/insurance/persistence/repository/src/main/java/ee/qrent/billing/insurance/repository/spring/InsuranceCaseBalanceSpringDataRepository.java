package ee.qrent.billing.insurance.repository.spring;

import ee.qrent.billing.insurance.persistence.entity.jakarta.InsuranceCaseBalanceJakartaEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InsuranceCaseBalanceSpringDataRepository
    extends JpaRepository<InsuranceCaseBalanceJakartaEntity, Long> {

  @Query(
      value =
          "select bl.* from insurance_case_balance bl LEFT JOIN q_week qw on bl.q_week_id = qw.id "
              + "where bl.insurance_case_id = :insuranceCaseId "
              + "order by qw.year desc, qw.number desc limit 1",
      nativeQuery = true)
  InsuranceCaseBalanceJakartaEntity findLatestByInsuranceCaseId(
      final @Param("insuranceCaseId") Long insuranceCaseId);

  List<InsuranceCaseBalanceJakartaEntity> findAllByInsuranceCaseId(final Long insuranceCaseId);

  @Query(
      value =
          "select bl.* from insurance_case_balance bl LEFT JOIN insurance_case ic on bl.insurance_case_id = ic.id "
              + "where bl.q_week_id = :qWeekId  and ic.driver_id = :driverId ",
      nativeQuery = true)
  List<InsuranceCaseBalanceJakartaEntity> findAllByQWeekIdAndInsuranceCaseDriverId(
      final @Param("qWeekId") Long qWeekId, final @Param("driverId") Long driverId);

  @Query(
      value =
          "select bl.* from insurance_case_balance bl "
              + "where bl.insurance_case_id = :insuranceCaseId and q_week_id = :qWeekId",
      nativeQuery = true)
  InsuranceCaseBalanceJakartaEntity findByInsuranceCaseIdAnqQWeekId(
      final @Param("insuranceCaseId") Long insuranceCaseId, final @Param("qWeekId") Long qWeekId);
}
