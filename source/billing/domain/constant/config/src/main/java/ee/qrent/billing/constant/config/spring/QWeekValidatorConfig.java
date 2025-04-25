package ee.qrent.billing.constant.config.spring;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.constant.api.in.request.QWeekAddRequest;
import ee.qrent.billing.constant.api.out.QWeekLoadPort;
import ee.qrent.billing.constant.core.validator.QWeekAddRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QWeekValidatorConfig {
  @Bean
  public AddRequestValidator<QWeekAddRequest> getQWeekAddRequestValidator(
      final QWeekLoadPort loadPort) {
    return new QWeekAddRequestValidator(loadPort);
  }
}
