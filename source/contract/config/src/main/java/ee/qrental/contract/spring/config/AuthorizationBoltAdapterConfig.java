package ee.qrental.contract.spring.config;

import ee.qrental.contract.adapter.adapter.AuthorizationBoltLoadAdapter;
import ee.qrental.contract.adapter.adapter.AuthorizationBoltPersistenceAdapter;
import ee.qrental.contract.adapter.mapper.AuthorizationBoltAdapterMapper;
import ee.qrental.contract.adapter.repository.AuthorizationBoltRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationBoltAdapterConfig {
  @Bean
  AuthorizationBoltAdapterMapper getCAuthorizationBoltAdapterMapper() {
    return new AuthorizationBoltAdapterMapper();
  }

  @Bean
  AuthorizationBoltLoadAdapter getAuthorizationBoltLoadAdapter(
      final AuthorizationBoltRepository repository, final AuthorizationBoltAdapterMapper mapper) {
    return new AuthorizationBoltLoadAdapter(repository, mapper);
  }

  @Bean
  AuthorizationBoltPersistenceAdapter getAuthorizationBoltPersistenceAdapter(
      final AuthorizationBoltRepository repository, final AuthorizationBoltAdapterMapper mapper) {
    return new AuthorizationBoltPersistenceAdapter(repository, mapper);
  }
}
