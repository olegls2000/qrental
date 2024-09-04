package ee.qrental.insurance.adapter.repository;

import ee.qrental.insurance.entity.jakarta.InsuranceCaseBalanceJakartaEntity;

import java.util.List;

public interface InsuranceCaseBalanceRepository {
  InsuranceCaseBalanceJakartaEntity findLatestByInsuranceCaseId(final Long insuranceCaseId);

  InsuranceCaseBalanceJakartaEntity findByInsuranceCaseIdAndQWeekId(
      final Long insuranceCaseId, final Long qWeekId);

  List<InsuranceCaseBalanceJakartaEntity> findAllByInsuranceCaseId(final Long insuranceCaseId);

  List<InsuranceCaseBalanceJakartaEntity> findAllByQWeekIdAndDriverId(
      final Long qWeekId, final Long driverId);

  InsuranceCaseBalanceJakartaEntity save(
      final InsuranceCaseBalanceJakartaEntity insuranceCaseBalance);
}
