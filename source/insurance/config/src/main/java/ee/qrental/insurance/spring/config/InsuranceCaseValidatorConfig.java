package ee.qrental.insurance.spring.config;

import ee.qrent.common.in.time.QDateTime;
import ee.qrental.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrental.insurance.core.validator.InsuranceCaseAddBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsuranceCaseValidatorConfig {

  @Bean
  InsuranceCaseAddBusinessRuleValidator getInsuranceCaseBusinessRuleValidator(
      final InsuranceCaseLoadPort insuranceCaseLoadPort, final QDateTime qDateTime) {
    return new InsuranceCaseAddBusinessRuleValidator(insuranceCaseLoadPort, qDateTime);
  }
}
