package ee.qrental.user.spring.config;

import ee.qrental.user.adapter.repository.RoleRepository;
import ee.qrental.user.repository.impl.RoleRepositoryImpl;
import ee.qrental.user.repository.spring.RoleSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleRepositoryConfig {

  @Bean
  RoleRepository getRoleRepository(final RoleSpringDataRepository springDataRepository) {
    return new RoleRepositoryImpl(springDataRepository);
  }
}
