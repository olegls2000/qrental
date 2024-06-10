package ee.qrental.driver.spring.config;

import ee.qrental.driver.api.out.*;
import ee.qrental.driver.core.validator.DriverUpdateBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverValidatorConfig {

  @Bean
  DriverUpdateBusinessRuleValidator getDriverBusinessRuleValidator(final DriverLoadPort loadPort) {
    return new DriverUpdateBusinessRuleValidator(loadPort);
  }
}
