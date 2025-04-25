package ee.qrent.billing.insurance.config.spring;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.CloseRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.insurance.api.in.request.InsuranceCalculationAddRequest;
import ee.qrent.billing.insurance.api.in.request.InsuranceCaseCloseRequest;
import ee.qrent.billing.insurance.api.in.request.InsuranceCaseUpdateRequest;
import ee.qrent.billing.insurance.api.out.InsuranceCaseBalanceLoadPort;
import ee.qrent.billing.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrent.billing.insurance.core.validator.InsuranceCalculationAddRequestValidator;
import ee.qrent.billing.insurance.core.validator.InsuranceCaseCloseRequestValidator;
import ee.qrent.billing.insurance.core.validator.InsuranceCaseUpdateRequestValidator;
import ee.qrent.billing.transaction.api.in.query.rent.GetRentCalculationQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsuranceCaseValidatorConfig {

  @Bean
  UpdateRequestValidator<InsuranceCaseUpdateRequest> getInsuranceCaseUpdateRequestValidator(
      final InsuranceCaseLoadPort insuranceCaseLoadPort,
      final InsuranceCaseBalanceLoadPort insuranceCaseBalanceLoadPort) {
    return new InsuranceCaseUpdateRequestValidator(
        insuranceCaseLoadPort, insuranceCaseBalanceLoadPort);
  }

  @Bean
  CloseRequestValidator<InsuranceCaseCloseRequest> getInsuranceCaseCloseRequestValidator(
      final InsuranceCaseLoadPort loadPort) {
    return new InsuranceCaseCloseRequestValidator(loadPort);
  }

  @Bean
  AddRequestValidator<InsuranceCalculationAddRequest> getInsuranceCalculationAddRequestValidator(
      final GetQWeekQuery qWeekQuery, final GetRentCalculationQuery rentCalculationQuery) {
    return new InsuranceCalculationAddRequestValidator(qWeekQuery, rentCalculationQuery);
  }
}
