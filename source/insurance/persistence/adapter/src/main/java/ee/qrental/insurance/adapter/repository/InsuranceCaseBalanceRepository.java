package ee.qrental.insurance.adapter.repository;

import ee.qrental.insurance.entity.jakarta.InsuranceCaseBalanceJakartaEntity;

import java.util.List;

public interface InsuranceCaseBalanceRepository {
  InsuranceCaseBalanceJakartaEntity findLatestByInsuranceCaseId(final Long insuranceCaseId);

  List<InsuranceCaseBalanceJakartaEntity> findAllByInsuranceCaseId(final Long insuranceCaseId);

  InsuranceCaseBalanceJakartaEntity save(
      final InsuranceCaseBalanceJakartaEntity insuranceCaseBalance);
}
