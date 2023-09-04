package ee.qrental.user.spring.config;

import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.out.*;
import ee.qrental.driver.core.validator.CallSignLinkBusinessRuleValidator;
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
