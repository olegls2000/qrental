package ee.qrental.callsign.spring.config.callsign;

import ee.qrental.callsign.api.out.*;
import ee.qrental.callsign.core.validator.CallSignBusinessRuleValidator;
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
