package ee.qrental.driver.spring.config;

import ee.qrental.driver.api.out.*;
import ee.qrental.driver.core.validator.CallSignBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallSignValidatorConfig {

  @Bean
  CallSignBusinessRuleValidator getCallSignBusinessRuleValidator(
      final CallSignLoadPort callSignLoadPort, final CallSignLinkLoadPort callSignLinkLoadPort) {
    return new CallSignBusinessRuleValidator(callSignLoadPort, callSignLinkLoadPort);
  }
}
