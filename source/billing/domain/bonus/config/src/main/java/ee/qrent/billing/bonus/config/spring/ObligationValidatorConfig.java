package ee.qrent.billing.bonus.config.spring;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.bonus.api.in.request.ObligationCalculationAddRequest;
import ee.qrent.billing.bonus.api.out.ObligationCalculationLoadPort;
import ee.qrent.billing.bonus.core.validator.ObligationCalculationAddRequestValidator;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObligationValidatorConfig {

  @Bean
  AddRequestValidator<ObligationCalculationAddRequest> getObligationCalculationAddRequestValidator(
      final GetQWeekQuery qWeekQuery, final ObligationCalculationLoadPort loadPort) {
    return new ObligationCalculationAddRequestValidator(qWeekQuery, loadPort);
  }
}
