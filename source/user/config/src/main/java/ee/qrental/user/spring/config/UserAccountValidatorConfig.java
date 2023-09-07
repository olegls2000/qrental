package ee.qrental.user.spring.config;

import ee.qrental.user.api.out.UserAccountLoadPort;
import ee.qrental.user.core.validator.UserAccountBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserAccountValidatorConfig {

  @Bean
  UserAccountBusinessRuleValidator getUserAccountBusinessRuleValidator(final UserAccountLoadPort loadPort) {
    return new UserAccountBusinessRuleValidator(loadPort);
  }
}
