package ee.qrental.bonus.spring.config;

import ee.qrental.bonus.api.out.ObligationCalculationLoadPort;
import ee.qrental.bonus.core.validator.BonusCalculationAddBusinessRuleValidator;
import ee.qrental.bonus.core.validator.ObligationCalculationAddBusinessRuleValidator;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObligationValidatorConfig {

  @Bean
  ObligationCalculationAddBusinessRuleValidator getObligationCalculationAddBusinessRuleValidator(
      final GetQWeekQuery qWeekQuery, final ObligationCalculationLoadPort loadPort) {
    return new ObligationCalculationAddBusinessRuleValidator(qWeekQuery, loadPort);
  }
}
