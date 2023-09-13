package ee.qrental.contract.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContractRepositoryConfig {

  @Bean
  ContractRepository getContractRepository(final ContractSpringDataRepository springDataRepository) {
    return new ContractRepositoryImpl(springDataRepository);
  }
}
