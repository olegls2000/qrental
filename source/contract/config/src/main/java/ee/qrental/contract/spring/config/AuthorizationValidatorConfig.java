package ee.qrental.contract.spring.config;

import ee.qrental.contract.api.out.AuthorizationLoadPort;
import ee.qrental.contract.core.validator.AuthorizationAddBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationValidatorConfig {

  @Bean
  AuthorizationAddBusinessRuleValidator getAuthorizationAddBusinessRuleValidator(
      final AuthorizationLoadPort loadPort) {
    return new AuthorizationAddBusinessRuleValidator(loadPort);
  }

}
