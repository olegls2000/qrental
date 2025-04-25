package ee.qrent.billing.user.config.spring;

import ee.qrent.billing.user.api.out.RoleLoadPort;
import ee.qrent.billing.user.api.out.UserAccountLoadPort;
import ee.qrent.billing.user.core.validator.RoleRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleValidatorConfig {

  @Bean
  RoleRequestValidator getRoleRequestValidator(
          final RoleLoadPort loadPort, 
          final UserAccountLoadPort userAccountLoadPort) {
    return new RoleRequestValidator(loadPort, userAccountLoadPort);
  }
}
