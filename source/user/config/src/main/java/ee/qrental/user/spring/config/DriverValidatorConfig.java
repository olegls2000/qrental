package ee.qrental.user.spring.config;

import ee.qrental.driver.api.out.*;
import ee.qrental.driver.core.validator.DriverBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverValidatorConfig {

  @Bean
  DriverBusinessRuleValidator getDriverBusinessRuleValidator(final DriverLoadPort loadPort) {
    return new DriverBusinessRuleValidator(loadPort);
  }
}
