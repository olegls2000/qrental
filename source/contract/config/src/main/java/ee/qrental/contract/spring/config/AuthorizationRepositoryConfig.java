package ee.qrental.contract.spring.config;

import ee.qrental.contract.adapter.repository.AuthorizationRepository;
import ee.qrental.contract.repository.impl.AuthorizationRepositoryImpl;
import ee.qrental.contract.repository.spring.AuthorizationSpringDataRepository;
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
