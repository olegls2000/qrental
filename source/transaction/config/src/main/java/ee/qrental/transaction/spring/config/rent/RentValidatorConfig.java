package ee.qrental.transaction.spring.config.rent;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.api.out.rent.RentCalculationLoadPort;
import ee.qrental.transaction.core.validator.RentCalculationAddBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RentValidatorConfig {
  @Bean
  RentCalculationAddBusinessRuleValidator getRentCalculationAddBusinessRuleValidator(
      final RentCalculationLoadPort loadPort,
      final BalanceLoadPort balanceLoadPort,
      final GetQWeekQuery qWeekQuery) {
    return new RentCalculationAddBusinessRuleValidator(loadPort, balanceLoadPort, qWeekQuery);
  }
}
