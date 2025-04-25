package ee.qrent.billing.contract.config.spring;

import ee.qrent.billing.contract.persistence.repository.ContractRepository;
import ee.qrent.billing.contract.persistence.repository.impl.ContractRepositoryImpl;
import ee.qrent.billing.contract.persistence.repository.spring.ContractSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContractRepositoryConfig {

  @Bean
  ContractRepository getContractRepository(
      final ContractSpringDataRepository springDataRepository) {
    return new ContractRepositoryImpl(springDataRepository);
  }
}
