package ee.qrent.billing.user.config.spring;

import ee.qrent.billing.user.api.out.UserAccountLoadPort;
import ee.qrent.billing.user.core.mapper.UserAccountAddRequestMapper;
import ee.qrent.billing.user.core.mapper.UserAccountResponseMapper;
import ee.qrent.billing.user.core.mapper.UserAccountUpdateRequestMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserAccountMapperConfig {
  @Bean
  UserAccountAddRequestMapper getUserAddRequestMapper() {
    return new UserAccountAddRequestMapper();
  }

  @Bean
  UserAccountResponseMapper getUserResponseMapper() {
    return new UserAccountResponseMapper();
  }

  @Bean
  UserAccountUpdateRequestMapper getUserUpdateRequestMapper(final UserAccountLoadPort userAccountloadPort) {
    return new UserAccountUpdateRequestMapper(userAccountloadPort);
  }
}
