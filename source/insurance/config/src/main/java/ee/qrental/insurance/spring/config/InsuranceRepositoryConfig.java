package ee.qrental.insurance.spring.config;

import ee.qrental.insurance.adapter.repository.InsuranceCalculationRepository;
import ee.qrental.insurance.adapter.repository.InsuranceCaseBalanceRepository;
import ee.qrental.insurance.adapter.repository.InsuranceCaseBalanceTransactionRepository;
import ee.qrental.insurance.adapter.repository.InsuranceCaseRepository;
import ee.qrental.insurance.repository.impl.InsuranceCalculationRepositoryImpl;
import ee.qrental.insurance.repository.impl.InsuranceCaseBalanceRepositoryImpl;
import ee.qrental.insurance.repository.impl.InsuranceCaseBalanceTransactionRepositoryImpl;
import ee.qrental.insurance.repository.impl.InsuranceCaseRepositoryImpl;
import ee.qrental.insurance.repository.spring.InsuranceCalculationSpringDataRepository;
import ee.qrental.insurance.repository.spring.InsuranceCaseBalanceSpringDataRepository;
import ee.qrental.insurance.repository.spring.InsuranceCaseBalanceTransactionSpringDataRepository;
import ee.qrental.insurance.repository.spring.InsuranceCaseSpringDataRepository;
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
