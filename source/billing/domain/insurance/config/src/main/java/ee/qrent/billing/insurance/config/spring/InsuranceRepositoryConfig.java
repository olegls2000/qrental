package ee.qrent.billing.insurance.config.spring;

import ee.qrent.billing.insurance.persistence.repository.InsuranceCalculationRepository;
import ee.qrent.billing.insurance.persistence.repository.InsuranceCaseBalanceRepository;
import ee.qrent.billing.insurance.persistence.repository.InsuranceCaseBalanceTransactionRepository;
import ee.qrent.billing.insurance.persistence.repository.InsuranceCaseRepository;
import ee.qrent.billing.insurance.repository.impl.InsuranceCalculationRepositoryImpl;
import ee.qrent.billing.insurance.repository.impl.InsuranceCaseBalanceRepositoryImpl;
import ee.qrent.billing.insurance.repository.impl.InsuranceCaseBalanceTransactionRepositoryImpl;
import ee.qrent.billing.insurance.repository.impl.InsuranceCaseRepositoryImpl;
import ee.qrent.billing.insurance.repository.spring.InsuranceCalculationSpringDataRepository;
import ee.qrent.billing.insurance.repository.spring.InsuranceCaseBalanceSpringDataRepository;
import ee.qrent.billing.insurance.repository.spring.InsuranceCaseBalanceTransactionSpringDataRepository;
import ee.qrent.billing.insurance.repository.spring.InsuranceCaseSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsuranceRepositoryConfig {

  @Bean
  InsuranceCaseRepository getInsuranceCaseRepository(
      final InsuranceCaseSpringDataRepository springDataRepository) {
    return new InsuranceCaseRepositoryImpl(springDataRepository);
  }

  @Bean
  InsuranceCaseBalanceRepository getInsuranceCaseBalanceRepository(
      final InsuranceCaseBalanceSpringDataRepository springDataRepository) {
    return new InsuranceCaseBalanceRepositoryImpl(springDataRepository);
  }

  @Bean
  InsuranceCalculationRepository getInsuranceCalculationRepository(
      final InsuranceCalculationSpringDataRepository springDataRepository) {
    return new InsuranceCalculationRepositoryImpl(springDataRepository);
  }

  @Bean
  InsuranceCaseBalanceTransactionRepository getInsuranceCaseBalanceTransactionRepository(
      final InsuranceCaseBalanceTransactionSpringDataRepository springDataRepository) {
    return new InsuranceCaseBalanceTransactionRepositoryImpl(springDataRepository);
  }
}
