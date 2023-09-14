package ee.qrental.contract.spring.config;

import ee.qrental.contract.adapter.repository.ContractRepository;
import ee.qrental.contract.repository.impl.ContractRepositoryImpl;
import ee.qrental.contract.repository.spring.ContractSpringDataRepository;
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
