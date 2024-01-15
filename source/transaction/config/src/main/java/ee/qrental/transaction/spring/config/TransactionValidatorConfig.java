package ee.qrental.transaction.spring.config;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.core.validator.TransactionAddBusinessRuleValidator;
import ee.qrental.transaction.core.validator.TransactionUpdateDeleteBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionValidatorConfig {

  @Bean
  TransactionUpdateDeleteBusinessRuleValidator getTransactionBusinessRuleValidator(
      final GetQWeekQuery qWeekQuery,
      final TransactionLoadPort transactionLoadPort,
      final BalanceLoadPort balanceLoadPort) {
    return new TransactionUpdateDeleteBusinessRuleValidator(qWeekQuery, transactionLoadPort, balanceLoadPort);
  }

  @Bean
  TransactionAddBusinessRuleValidator getTransactionAddBusinessRuleValidator(
      final GetQWeekQuery qWeekQuery, final BalanceLoadPort balanceLoadPort) {
    return new TransactionAddBusinessRuleValidator(qWeekQuery, balanceLoadPort);
  }
}
