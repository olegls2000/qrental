package ee.qrental.driver.spring.config;

import ee.qrent.common.in.time.QDateTime;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.driver.api.out.*;
import ee.qrental.driver.core.validator.DriverUpdateBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverValidatorConfig {

  @Bean
  DriverUpdateBusinessRuleValidator getDriverBusinessRuleValidator(
      final DriverLoadPort loadPort, final GetQWeekQuery qWeekQuery, final QDateTime qDateTime) {
    return new DriverUpdateBusinessRuleValidator(loadPort, qWeekQuery, qDateTime);
  }
}
