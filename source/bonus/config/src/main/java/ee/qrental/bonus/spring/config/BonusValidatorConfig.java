package ee.qrental.bonus.spring.config;

import ee.qrental.bonus.api.out.BonusCalculationLoadPort;
import ee.qrental.bonus.api.out.ObligationCalculationLoadPort;
import ee.qrental.bonus.core.validator.BonusCalculationAddBusinessRuleValidator;
import ee.qrental.bonus.core.validator.ObligationCalculationAddBusinessRuleValidator;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BonusValidatorConfig {
  @Bean
  BonusCalculationAddBusinessRuleValidator getBonusCalculationAddBusinessRuleValidator(
      final GetQWeekQuery qWeekQuery, final BonusCalculationLoadPort loadPort) {
    return new BonusCalculationAddBusinessRuleValidator(qWeekQuery, loadPort);
  }
}
