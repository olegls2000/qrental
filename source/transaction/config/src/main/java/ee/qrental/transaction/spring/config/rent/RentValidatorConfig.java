package ee.qrental.transaction.spring.config.rent;

import ee.qrental.transaction.core.validator.RentCalculationAddBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RentValidatorConfig {
  @Bean
  RentCalculationAddBusinessRuleValidator getRentCalculationAddBusinessRuleValidator() {
    return new RentCalculationAddBusinessRuleValidator();
  }
}
