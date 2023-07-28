package ee.qrental.transaction.spring.config.balance;

import ee.qrental.transaction.api.out.balance.BalanceCalculationLoadPort;
import ee.qrental.transaction.core.validator.BalanceCalculationBusinessRuleValidator;
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
