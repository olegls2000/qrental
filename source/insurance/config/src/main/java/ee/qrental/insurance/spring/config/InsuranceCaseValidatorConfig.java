package ee.qrental.insurance.spring.config;

import ee.qrent.common.in.time.QDateTime;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrental.insurance.core.validator.InsuranceCalculationAddBusinessRuleValidator;
import ee.qrental.insurance.core.validator.InsuranceCaseAddBusinessRuleValidator;
import ee.qrental.transaction.api.in.query.rent.GetRentCalculationQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsuranceCaseValidatorConfig {

  @Bean
  InsuranceCaseAddBusinessRuleValidator getInsuranceCaseBusinessRuleValidator(
      final InsuranceCaseLoadPort insuranceCaseLoadPort, final QDateTime qDateTime) {
    return new InsuranceCaseAddBusinessRuleValidator(insuranceCaseLoadPort, qDateTime);
  }

  @Bean
  InsuranceCalculationAddBusinessRuleValidator getInsuranceCalculationAddBusinessRuleValidator(
      final GetQWeekQuery qWeekQuery, final GetRentCalculationQuery rentCalculationQuery) {
    return new InsuranceCalculationAddBusinessRuleValidator(qWeekQuery, rentCalculationQuery);
  }
}
