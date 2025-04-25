package ee.qrent.billing.firm.config.spring;

import ee.qrent.billing.firm.core.validator.FirmRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirmValidatorConfig {
  @Bean
  FirmRequestValidator getFirmRequestValidator() {
    return new FirmRequestValidator();
  }
}
