package ee.qrental.link.spring.config;

import ee.qrental.link.api.out.LinkLoadPort;
import ee.qrental.link.core.validator.LinkAddBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkValidatorConfig {

  @Bean
  LinkAddBusinessRuleValidator getLinkAddBusinessRuleValidator(final LinkLoadPort loadPort) {
    return new LinkAddBusinessRuleValidator(loadPort);
  }
}
