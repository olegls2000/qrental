package ee.qrent.billing.user.config.spring;

import ee.qrent.billing.user.api.out.UserAccountLoadPort;
import ee.qrent.billing.user.core.validator.UserAccountRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserAccountValidatorConfig {

  @Bean
  UserAccountRequestValidator getUserAccountRequestValidator(final UserAccountLoadPort loadPort) {
    return new UserAccountRequestValidator(loadPort);
  }
}
