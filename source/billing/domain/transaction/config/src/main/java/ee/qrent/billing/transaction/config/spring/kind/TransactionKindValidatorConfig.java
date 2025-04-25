package ee.qrent.billing.transaction.config.spring.kind;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.billing.transaction.api.in.request.kind.TransactionKindAddRequest;
import ee.qrent.billing.transaction.api.in.request.kind.TransactionKindUpdateRequest;
import ee.qrent.billing.transaction.api.out.kind.TransactionKindLoadPort;
import ee.qrent.billing.transaction.core.validator.TransactionKindAddRequestValidator;
import ee.qrent.billing.transaction.core.validator.TransactionKindUpdateRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionKindValidatorConfig {

  @Bean
  AddRequestValidator<TransactionKindAddRequest> getTransactionKindAddRequestValidator(
      final TransactionKindLoadPort loadPort) {

    return new TransactionKindAddRequestValidator(loadPort);
  }

  @Bean
  UpdateRequestValidator<TransactionKindUpdateRequest> getTransactionKindUpdateRequestValidator(
      final TransactionKindLoadPort loadPort) {

    return new TransactionKindUpdateRequestValidator(loadPort);
  }
}
