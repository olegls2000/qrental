package ee.qrental.transaction.spring.config;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.core.validator.TransactionBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionValidatorConfig {

  @Bean
  TransactionBusinessRuleValidator getTransactionBusinessRuleValidator(
      final GetQWeekQuery qWeekQuery,
      final TransactionLoadPort transactionLoadPort,
      final BalanceLoadPort balanceLoadPort) {
    return new TransactionBusinessRuleValidator(qWeekQuery, transactionLoadPort, balanceLoadPort);
  }
}
