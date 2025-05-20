package ee.qrent.billing.report.spring.config;

import ee.qrent.billing.bonus.api.in.query.GetObligationCalculationQuery;
import ee.qrent.billing.report.api.in.request.WeeklyReportAddRequest;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.report.core.validator.WeeklyReportAddRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeeklyReportValidatorConfig {

  @Bean
  AddRequestValidator<WeeklyReportAddRequest> getWeeklyReportAddRequestValidator(
      final GetObligationCalculationQuery obligationCalculationQuery,
      final GetQWeekQuery qWeekQuery) {

    return new WeeklyReportAddRequestValidator(obligationCalculationQuery, qWeekQuery);
  }
}
