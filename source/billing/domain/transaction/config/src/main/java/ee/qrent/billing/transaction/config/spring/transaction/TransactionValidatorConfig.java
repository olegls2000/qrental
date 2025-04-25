package ee.qrent.billing.transaction.config.spring.transaction;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.DeleteRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.transaction.api.in.request.TransactionAddRequest;
import ee.qrent.billing.transaction.api.in.request.TransactionDeleteRequest;
import ee.qrent.billing.transaction.api.in.request.TransactionUpdateRequest;
import ee.qrent.billing.transaction.api.out.TransactionLoadPort;
import ee.qrent.billing.transaction.api.out.balance.BalanceLoadPort;
import ee.qrent.billing.transaction.core.validator.TransactionAddRequestValidator;
import ee.qrent.billing.transaction.core.validator.TransactionDeleteRequestValidator;
import ee.qrent.billing.transaction.core.validator.TransactionUpdateRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionValidatorConfig {

  @Bean
  UpdateRequestValidator<TransactionUpdateRequest> getTransactionUpdateRequestValidator(
      final GetQWeekQuery qWeekQuery,
      final BalanceLoadPort balanceLoadPort,
      final TransactionLoadPort transactionLoadPort) {
    return new TransactionUpdateRequestValidator(qWeekQuery, balanceLoadPort, transactionLoadPort);
  }

  @Bean
  AddRequestValidator<TransactionAddRequest> getTransactionAddRequestValidator(
      final GetQWeekQuery qWeekQuery,
      final BalanceLoadPort balanceLoadPort,
      final TransactionLoadPort transactionLoadPort) {
    return new TransactionAddRequestValidator(qWeekQuery, balanceLoadPort, transactionLoadPort);
  }

  @Bean
  DeleteRequestValidator<TransactionDeleteRequest> getTransactionDeleteRequestValidator(
      final GetQWeekQuery qWeekQuery,
      final BalanceLoadPort balanceLoadPort,
      final TransactionLoadPort transactionLoadPort) {
    return new TransactionDeleteRequestValidator(qWeekQuery, balanceLoadPort, transactionLoadPort);
  }
}
