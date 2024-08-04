package ee.qrental.insurance.spring.config;

import ee.qrental.insurance.adapter.repository.InsuranceCaseRepository;
import ee.qrental.insurance.repository.impl.InsuranceCaseRepositoryImpl;
import ee.qrental.insurance.repository.spring.InsuranceCaseSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsuranceCaseRepositoryConfig {

  @Bean
  InsuranceCaseRepository getInsuranceCaseRepository(
      final InsuranceCaseSpringDataRepository springDataRepository) {
    return new InsuranceCaseRepositoryImpl(springDataRepository);
  }
}
