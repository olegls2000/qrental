package ee.qrent.billing.contract.config.spring;

import ee.qrent.billing.contract.persistence.adapter.ContractLoadAdapter;
import ee.qrent.billing.contract.persistence.adapter.ContractPersistenceAdapter;
import ee.qrent.billing.contract.persistence.mapper.ContractAdapterMapper;
import ee.qrent.billing.contract.persistence.repository.ContractRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContractAdapterConfig {
  @Bean
  ContractAdapterMapper getContractAdapterMapper() {
    return new ContractAdapterMapper();
  }

  @Bean
  ContractLoadAdapter getContractLoadAdapter(
      final ContractRepository repository, final ContractAdapterMapper mapper) {
    return new ContractLoadAdapter(repository, mapper);
  }

  @Bean
  ContractPersistenceAdapter getContractPersistenceAdapter(
      final ContractRepository repository, final ContractAdapterMapper mapper) {
    return new ContractPersistenceAdapter(repository, mapper);
  }
}
