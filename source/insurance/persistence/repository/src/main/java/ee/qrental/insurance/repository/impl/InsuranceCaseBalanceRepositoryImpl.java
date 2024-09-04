package ee.qrental.insurance.repository.impl;

import ee.qrental.insurance.adapter.repository.InsuranceCaseBalanceRepository;
import ee.qrental.insurance.entity.jakarta.InsuranceCaseBalanceJakartaEntity;

import ee.qrental.insurance.repository.spring.InsuranceCaseBalanceSpringDataRepository;

import java.util.List;

public record InsuranceCaseBalanceRepositoryImpl(
    InsuranceCaseBalanceSpringDataRepository springDataRepository)
    implements InsuranceCaseBalanceRepository {

  @Override
  public InsuranceCaseBalanceJakartaEntity findLatestByInsuranceCaseId(final Long insuranceCaseId) {
    return springDataRepository.findLatestByInsuranceCaseId(insuranceCaseId);
  }

  @Override
  public InsuranceCaseBalanceJakartaEntity findByInsuranceCaseIdAndQWeekId(final Long insuranceCaseId,final Long qWeekId) {
    return  springDataRepository.findByInsuranceCaseIdAnqQWeekId(insuranceCaseId, qWeekId);
  }

  @Override
  public List<InsuranceCaseBalanceJakartaEntity> findAllByInsuranceCaseId(Long insuranceCaseId) {
    return springDataRepository.findAllByInsuranceCaseId(insuranceCaseId);
  }

  @Override
  public List<InsuranceCaseBalanceJakartaEntity> findAllByQWeekIdAndDriverId(
      final Long qWeekId, final Long driverId) {
    return springDataRepository.findAllByQWeekIdAndInsuranceCaseDriverId(qWeekId, driverId);
  }

  @Override
  public InsuranceCaseBalanceJakartaEntity save(
      InsuranceCaseBalanceJakartaEntity insuranceCaseBalance) {
    return springDataRepository.save(insuranceCaseBalance);
  }
}
