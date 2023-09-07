package ee.qrental.user.spring.config;

import ee.qrental.user.api.out.RoleLoadPort;
import ee.qrental.user.core.validator.RoleBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleValidatorConfig {

  @Bean
  RoleBusinessRuleValidator getRoleBusinessRuleValidator(final RoleLoadPort loadPort) {
    return new RoleBusinessRuleValidator(loadPort);
  }
}
