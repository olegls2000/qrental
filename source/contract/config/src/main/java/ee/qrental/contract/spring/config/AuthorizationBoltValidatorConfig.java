package ee.qrental.contract.spring.config;

import ee.qrental.contract.api.out.AuthorizationBoltLoadPort;
import ee.qrental.contract.core.validator.AuthorizationBoltAddBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationBoltValidatorConfig {

  @Bean
  AuthorizationBoltAddBusinessRuleValidator getAuthorizationBoltAddBusinessRuleValidator(
      final AuthorizationBoltLoadPort loadPort) {
    return new AuthorizationBoltAddBusinessRuleValidator(loadPort);
  }

}
