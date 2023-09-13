package ee.qrental.contract.spring.config;

import ee.qrental.invoice.adapter.adapter.ContractCalculationLoadAdapter;
import ee.qrental.invoice.adapter.adapter.ContractCalculationPersistenceAdapter;
import ee.qrental.invoice.adapter.adapter.ContractLoadAdapter;
import ee.qrental.invoice.adapter.adapter.ContractPersistenceAdapter;
import ee.qrental.invoice.adapter.mapper.ContractAdapterMapper;
import ee.qrental.invoice.adapter.mapper.ContractCalculationAdapterMapper;
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
      final ContractRepository repository,
      final ContractItemRepository itemRepository,
      final ContractAdapterMapper mapper) {
    return new ContractPersistenceAdapter(repository, itemRepository, mapper);
  }
}
