package ee.qrent.billing.driver.config.spring;

import ee.qrent.billing.driver.api.out.CallSignLinkLoadPort;
import ee.qrent.billing.driver.core.validator.CallSignLinkRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallSignLinkValidatorConfig {

  @Bean
  CallSignLinkRequestValidator getCallSignLinkRequestValidator(
      final CallSignLinkLoadPort loadPort) {
    return new CallSignLinkRequestValidator(loadPort);
  }
}
