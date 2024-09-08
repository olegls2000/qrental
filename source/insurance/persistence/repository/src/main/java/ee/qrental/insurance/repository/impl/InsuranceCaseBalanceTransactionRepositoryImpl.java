package ee.qrental.insurance.repository.impl;

import ee.qrental.insurance.adapter.repository.InsuranceCaseBalanceTransactionRepository;
import ee.qrental.insurance.entity.jakarta.InsuranceCaseBalanceTransactionJakartaEntity;
import ee.qrental.insurance.repository.spring.InsuranceCaseBalanceTransactionSpringDataRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseBalanceTransactionRepositoryImpl
    implements InsuranceCaseBalanceTransactionRepository {
  private final InsuranceCaseBalanceTransactionSpringDataRepository springDataRepository;

  @Override
  public InsuranceCaseBalanceTransactionJakartaEntity save(
      final InsuranceCaseBalanceTransactionJakartaEntity entity) {
    return springDataRepository.save(entity);
  }
}
