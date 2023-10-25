package ee.qrental.driver.spring.config;

import ee.qrental.driver.api.out.*;
import ee.qrental.driver.core.validator.CallSignLinkBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallSignLinkValidatorConfig {

  @Bean
  CallSignLinkBusinessRuleValidator getCallSignLinkBusinessRuleValidator(
      final CallSignLinkLoadPort loadPort) {
    return new CallSignLinkBusinessRuleValidator(loadPort);
  }
}
