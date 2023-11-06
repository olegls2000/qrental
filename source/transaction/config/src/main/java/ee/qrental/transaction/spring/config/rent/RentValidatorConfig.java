package ee.qrental.transaction.spring.config.rent;

import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.api.out.rent.RentCalculationLoadPort;
import ee.qrental.transaction.core.validator.RentCalculationAddBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RentValidatorConfig {
  @Bean
  RentCalculationAddBusinessRuleValidator getRentCalculationAddBusinessRuleValidator(
      final RentCalculationLoadPort loadPort, final BalanceLoadPort balanceLoadPort) {
    return new RentCalculationAddBusinessRuleValidator(loadPort, balanceLoadPort);
  }
}
