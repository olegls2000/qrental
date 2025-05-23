package ee.qrent.billing.report.spring.config;

import ee.qrent.billing.bonus.api.in.query.GetObligationCalculationQuery;
import ee.qrent.billing.report.api.in.request.WeeklyReportCalculationAddRequest;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.report.core.validator.WeeklyReportCalculationAddRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeeklyReportValidatorConfig {

  @Bean
  AddRequestValidator<WeeklyReportCalculationAddRequest> getWeeklyReportAddRequestValidator(
      final GetObligationCalculationQuery obligationCalculationQuery,
      final GetQWeekQuery qWeekQuery) {

    return new WeeklyReportCalculationAddRequestValidator(obligationCalculationQuery, qWeekQuery);
  }
}
