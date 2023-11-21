package ee.qrental.contract.spring.config;

import ee.qrental.contract.adapter.adapter.AuthorizationLoadAdapter;
import ee.qrental.contract.adapter.adapter.AuthorizationPersistenceAdapter;
import ee.qrental.contract.adapter.mapper.AuthorizationAdapterMapper;
import ee.qrental.contract.adapter.repository.AuthorizationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationAdapterConfig {
  @Bean
  AuthorizationAdapterMapper getCAuthorizationBoltAdapterMapper() {
    return new AuthorizationAdapterMapper();
  }

  @Bean
  AuthorizationLoadAdapter getAuthorizationBoltLoadAdapter(
          final AuthorizationRepository repository, final AuthorizationAdapterMapper mapper) {
    return new AuthorizationLoadAdapter(repository, mapper);
  }

  @Bean
  AuthorizationPersistenceAdapter getAuthorizationBoltPersistenceAdapter(
          final AuthorizationRepository repository, final AuthorizationAdapterMapper mapper) {
    return new AuthorizationPersistenceAdapter(repository, mapper);
  }
}
