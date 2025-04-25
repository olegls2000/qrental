package ee.qrent.billing.contract.config.spring;

import ee.qrent.billing.contract.persistence.repository.AuthorizationRepository;
import ee.qrent.billing.contract.persistence.repository.impl.AuthorizationRepositoryImpl;
import ee.qrent.billing.contract.persistence.repository.spring.AuthorizationSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationRepositoryConfig {

  @Bean
  AuthorizationRepository getAuthorizationRepository(
      final AuthorizationSpringDataRepository springDataRepository) {
    return new AuthorizationRepositoryImpl(springDataRepository) {};
  }
}
