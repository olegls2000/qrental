package ee.qrent.billing.deposit.config.spring;

import ee.qrent.billing.deposit.core.validator.DepositRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DepositValidatorConfig {
  @Bean
  DepositRequestValidator getDepositRequestValidator() {
    return new DepositRequestValidator();
  }
}
