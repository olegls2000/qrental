package ee.qrental.firm.spring.config;

import ee.qrental.constant.api.out.QWeekLoadPort;
import ee.qrental.constant.core.validator.QWeekAddBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QWeekValidatorConfig {
  @Bean
  public QWeekAddBusinessRuleValidator getQQWeekAddBusinessRuleValidator(
      final QWeekLoadPort loadPort) {
    return new QWeekAddBusinessRuleValidator(loadPort);
  }
}
