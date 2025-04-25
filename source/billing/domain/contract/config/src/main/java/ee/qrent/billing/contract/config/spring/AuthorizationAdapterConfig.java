package ee.qrent.billing.contract.config.spring;

import ee.qrent.billing.contract.persistence.adapter.AuthorizationLoadAdapter;
import ee.qrent.billing.contract.persistence.adapter.AuthorizationPersistenceAdapter;
import ee.qrent.billing.contract.persistence.mapper.AuthorizationAdapterMapper;
import ee.qrent.billing.contract.persistence.repository.AuthorizationRepository;
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
