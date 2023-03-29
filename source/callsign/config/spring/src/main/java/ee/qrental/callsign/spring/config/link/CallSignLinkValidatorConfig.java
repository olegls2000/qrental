package ee.qrental.callsign.spring.config.link;

import ee.qrental.callsign.api.out.*;
import ee.qrental.callsign.core.validator.CallSignLinkBusinessRuleValidator;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallSignLinkValidatorConfig {

  @Bean
  CallSignLinkBusinessRuleValidator getCallSignLinkBusinessRuleValidator(
      final CallSignLinkLoadPort callSignLinkLoadPort,
      final CallSignLoadPort callSignLoadPort,
      final GetDriverQuery driverQuery) {
    return new CallSignLinkBusinessRuleValidator(
        callSignLinkLoadPort, callSignLoadPort, driverQuery);
  }
}
