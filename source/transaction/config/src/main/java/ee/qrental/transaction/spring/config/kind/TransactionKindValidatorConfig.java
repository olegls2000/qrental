package ee.qrental.transaction.spring.config.kind;

import ee.qrental.transaction.api.out.kind.TransactionKindLoadPort;
import ee.qrental.transaction.core.validator.TransactionKindAddBusinessRuleValidator;
import ee.qrental.transaction.core.validator.TransactionKindUpdateBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionKindValidatorConfig {

  @Bean
  TransactionKindAddBusinessRuleValidator getTransactionKindAddBusinessRuleValidator(
      final TransactionKindLoadPort loadPort) {
    return new TransactionKindAddBusinessRuleValidator(loadPort);
  }

  @Bean
  TransactionKindUpdateBusinessRuleValidator getTransactionKindUpdateBusinessRuleValidator(
      final TransactionKindLoadPort loadPort) {
    return new TransactionKindUpdateBusinessRuleValidator(loadPort);
  }
}
