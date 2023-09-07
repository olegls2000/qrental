package ee.qrental.user.spring.config;

import ee.qrental.user.adapter.repository.UserAccountRepository;
import ee.qrental.user.repository.impl.UserAccountRepositoryImpl;
import ee.qrental.user.repository.spring.UserAccountSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserAccountRepositoryConfig {

  @Bean
  UserAccountRepository getUserAccountRepository(final UserAccountSpringDataRepository springDataRepository) {
    return new UserAccountRepositoryImpl(springDataRepository);
  }
}
