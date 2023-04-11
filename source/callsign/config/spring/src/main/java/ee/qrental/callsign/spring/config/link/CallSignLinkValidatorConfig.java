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
      final CallSignLinkLoadPort loadPort, final GetDriverQuery driverQuery) {
    return new CallSignLinkBusinessRuleValidator(loadPort, driverQuery);
  }
}
