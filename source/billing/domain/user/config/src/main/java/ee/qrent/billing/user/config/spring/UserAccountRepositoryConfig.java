package ee.qrent.billing.user.config.spring;

import ee.qrent.billing.user.persistence.repository.UserAccountRepository;
import ee.qrent.billing.persistence.user.repository.impl.UserAccountRepositoryImpl;
import ee.qrent.billing.persistence.user.repository.spring.UserAccountSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserAccountRepositoryConfig {

  @Bean
  UserAccountRepository getUserAccountRepository(final UserAccountSpringDataRepository springDataRepository) {
    return new UserAccountRepositoryImpl(springDataRepository);
  }
}
