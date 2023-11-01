package ee.qrental.transaction.spring.config.type;

import ee.qrental.transaction.core.validator.TransactionTypeAddBusinessRuleValidator;
import ee.qrental.transaction.core.validator.TransactionTypeUpdateBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionTypeValidatorConfig {

  @Bean
  TransactionTypeAddBusinessRuleValidator getTransactionTypeAddBusinessRuleValidator() {
    return new TransactionTypeAddBusinessRuleValidator();
  }

  @Bean
  TransactionTypeUpdateBusinessRuleValidator getTransactionTypeUpdateBusinessRuleValidator() {
    return new TransactionTypeUpdateBusinessRuleValidator();
  }
}
