package ee.qrent.billing.contract.config.spring;

import ee.qrent.billing.contract.api.out.AuthorizationLoadPort;
import ee.qrent.billing.contract.core.validator.AuthorizationAddRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationValidatorConfig {

  @Bean
  AuthorizationAddRequestValidator getAuthorizationAddRequestValidator(
      final AuthorizationLoadPort loadPort) {
    return new AuthorizationAddRequestValidator(loadPort);
  }

}
