package ee.qrental.contract.spring.config;

import ee.qrental.contract.adapter.adapter.ContractLoadAdapter;
import ee.qrental.contract.adapter.adapter.ContractPersistenceAdapter;
import ee.qrental.contract.adapter.mapper.ContractAdapterMapper;
import ee.qrental.contract.adapter.repository.ContractRepository;
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
