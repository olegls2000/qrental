package ee.qrental.contract.spring.config;

import ee.qrental.contract.adapter.adapter.ContractLoadAdapter;
import ee.qrental.contract.adapter.adapter.ContractPersistenceAdapter;
import ee.qrental.contract.adapter.mapper.AuthorizationBoltAdapterMapper;
import ee.qrental.contract.adapter.mapper.ContractAdapterMapper;
import ee.qrental.contract.adapter.repository.AuthorizationBoltRepository;
import ee.qrental.contract.adapter.repository.ContractRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationBoltAdapterConfig {
  @Bean
  AuthorizationBoltAdapterMapper getCAuthorizationBoltAdapterMapper(final AuthorizationBoltRepository repository) {
    return new AuthorizationBoltAdapterMapper(repository);
  }

  @Bean
  AuthorizationBoltLoadAdapter getAuthorizationBoltLoadAdapter(
      final ContractRepository repository, final ContractAdapterMapper mapper) {
    return new ContractLoadAdapter(repository, mapper);
  }

  @Bean
  AuthorizationBoltPersistenceAdapter getAuthorizationBoltPersistenceAdapter(
      final AuthorizationBoltRepository repository, final AuthorizationBoltAdapterMapper mapper) {
    return new AuthorizationBoltPersistenceAdapter(repository, mapper);
  }
}
