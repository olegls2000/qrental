package ee.qrental.insurance.spring.config;

import ee.qrental.insurance.adapter.adapter.InsuranceCaseLoadAdapter;
import ee.qrental.insurance.adapter.adapter.InsuranceCasePersistenceAdapter;
import ee.qrental.insurance.adapter.mapper.InsuranceCaseAdapterMapper;
import ee.qrental.insurance.adapter.repository.InsuranceCaseRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsuranceCaseAdapterConfig {
  @Bean
  InsuranceCaseAdapterMapper getInsuranceCaseAdapterMapper() {
    return new InsuranceCaseAdapterMapper();
  }

  @Bean
  InsuranceCaseLoadAdapter getInsuranceCaseLoadAdapter(
      final InsuranceCaseRepository repository, final InsuranceCaseAdapterMapper mapper) {
    return new InsuranceCaseLoadAdapter(repository, mapper);
  }

  @Bean
  InsuranceCasePersistenceAdapter getInsuranceCasePersistenceAdapter(
      final InsuranceCaseRepository repository, final InsuranceCaseAdapterMapper mapper) {
    return new InsuranceCasePersistenceAdapter(repository, mapper);
  }
}
