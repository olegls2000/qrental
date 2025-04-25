package ee.qrent.billing.user.config.spring;

import ee.qrent.billing.user.persistence.repository.RoleRepository;
import ee.qrent.billing.persistence.user.repository.impl.RoleRepositoryImpl;
import ee.qrent.billing.persistence.user.repository.spring.RoleSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleRepositoryConfig {

  @Bean
  RoleRepository getRoleRepository(final RoleSpringDataRepository springDataRepository) {
    return new RoleRepositoryImpl(springDataRepository);
  }
}
