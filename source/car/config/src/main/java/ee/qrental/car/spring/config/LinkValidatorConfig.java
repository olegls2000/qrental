package ee.qrental.car.spring.config;

import ee.qrental.car.api.out.CarLinkLoadPort;
import ee.qrental.car.core.validator.CarLinkAddBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkValidatorConfig {

  @Bean
  CarLinkAddBusinessRuleValidator getLinkAddBusinessRuleValidator(final CarLinkLoadPort loadPort) {
    return new CarLinkAddBusinessRuleValidator(loadPort);
  }
}
