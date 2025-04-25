package ee.qrent.billing.bonus.config.spring;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.bonus.api.in.request.BonusCalculationAddRequest;
import ee.qrent.billing.bonus.api.out.BonusCalculationLoadPort;
import ee.qrent.billing.bonus.core.validator.BonusCalculationAddRequestValidator;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BonusValidatorConfig {
  @Bean
  AddRequestValidator<BonusCalculationAddRequest> getBonusCalculationAddRequestValidator(
      final GetQWeekQuery qWeekQuery, final BonusCalculationLoadPort loadPort) {
    return new BonusCalculationAddRequestValidator(qWeekQuery, loadPort);
  }
}
