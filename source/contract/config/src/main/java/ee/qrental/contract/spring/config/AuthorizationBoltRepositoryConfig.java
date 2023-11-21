package ee.qrental.contract.spring.config;

import ee.qrental.contract.adapter.repository.AuthorizationBoltRepository;
import ee.qrental.contract.adapter.repository.ContractRepository;
import ee.qrental.contract.repository.impl.AuthorizationBoltRepositoryImpl;
import ee.qrental.contract.repository.impl.ContractRepositoryImpl;
import ee.qrental.contract.repository.spring.AuthorizationBoltSpringDataRepository;
import ee.qrental.contract.repository.spring.ContractSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationBoltRepositoryConfig {

  @Bean
  AuthorizationBoltRepository getAuthorizationBoltRepository(
      final AuthorizationBoltSpringDataRepository springDataRepository) {
    return new AuthorizationBoltRepositoryImpl(springDataRepository) {};
  }
}
