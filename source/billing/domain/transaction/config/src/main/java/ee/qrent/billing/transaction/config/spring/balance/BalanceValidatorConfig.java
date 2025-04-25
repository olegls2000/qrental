package ee.qrent.billing.transaction.config.spring.balance;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.transaction.api.in.request.balance.BalanceCalculationAddRequest;
import ee.qrent.billing.transaction.api.out.balance.BalanceCalculationLoadPort;
import ee.qrent.billing.transaction.core.validator.BalanceCalculationAddRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BalanceValidatorConfig {
  @Bean
  AddRequestValidator<BalanceCalculationAddRequest> getBalanceCalculationAddRequestValidator(
      final BalanceCalculationLoadPort loadPort) {
    return new BalanceCalculationAddRequestValidator(loadPort);
  }
}
