package ee.qrent.billing.driver.config.spring;

import ee.qrent.billing.driver.api.out.CallSignLinkLoadPort;
import ee.qrent.billing.driver.api.out.CallSignLoadPort;
import ee.qrent.common.in.validation.AttributeChecker;
import ee.qrent.billing.driver.core.validator.CallSignRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallSignValidatorConfig {

  @Bean
  CallSignRequestValidator getCallSignRequestValidator(
      final CallSignLoadPort callSignLoadPort,
      final CallSignLinkLoadPort callSignLinkLoadPort,
      final AttributeChecker attributeChecker) {

    return new CallSignRequestValidator(callSignLoadPort, callSignLinkLoadPort, attributeChecker);
  }
}
