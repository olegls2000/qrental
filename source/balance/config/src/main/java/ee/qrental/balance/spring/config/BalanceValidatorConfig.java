package ee.qrental.balance.spring.config;

import ee.qrental.balance.api.out.BalanceCalculationLoadPort;
import ee.qrental.balance.core.validator.BalanceCalculationBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BalanceValidatorConfig {
  @Bean
  BalanceCalculationBusinessRuleValidator getBalanceCalculationBusinessRuleValidator(
      final BalanceCalculationLoadPort loadPort) {
    return new BalanceCalculationBusinessRuleValidator(loadPort);
  }
}
