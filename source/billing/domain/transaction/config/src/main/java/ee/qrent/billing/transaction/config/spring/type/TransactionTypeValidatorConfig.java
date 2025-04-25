package ee.qrent.billing.transaction.config.spring.type;

import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.billing.transaction.api.in.request.type.TransactionTypeUpdateRequest;
import ee.qrent.billing.transaction.core.validator.TransactionTypeAddRequestValidator;
import ee.qrent.billing.transaction.core.validator.TransactionTypeUpdateRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionTypeValidatorConfig {

  @Bean
  TransactionTypeAddRequestValidator getTransactionTypeAddRequestValidator() {

    return new TransactionTypeAddRequestValidator();
  }

  @Bean
  UpdateRequestValidator<TransactionTypeUpdateRequest> getTransactionTypeUpdateRequestValidator() {

    return new TransactionTypeUpdateRequestValidator();
  }
}
