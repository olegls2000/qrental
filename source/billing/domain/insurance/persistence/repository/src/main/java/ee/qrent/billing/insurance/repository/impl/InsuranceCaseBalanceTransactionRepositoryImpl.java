package ee.qrent.billing.insurance.repository.impl;

import ee.qrent.billing.insurance.persistence.repository.InsuranceCaseBalanceTransactionRepository;
import ee.qrent.billing.insurance.persistence.entity.jakarta.InsuranceCaseBalanceTransactionJakartaEntity;
import ee.qrent.billing.insurance.repository.spring.InsuranceCaseBalanceTransactionSpringDataRepository;
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
