package ee.qrental.insurance.spring.config;

import ee.qrental.insurance.api.in.query.GetInsuranceCaseQuery;
import ee.qrental.insurance.api.out.InsuranceCaseAddPort;
import ee.qrental.insurance.api.out.InsuranceCaseDeletePort;
import ee.qrental.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrental.insurance.api.out.InsuranceCaseUpdatePort;
import ee.qrental.insurance.core.mapper.InsuranceCaseAddRequestMapper;
import ee.qrental.insurance.core.mapper.InsuranceCaseResponseMapper;
import ee.qrental.insurance.core.mapper.InsuranceCaseUpdateRequestMapper;
import ee.qrental.insurance.core.service.InsuranceCaseQueryService;
import ee.qrental.insurance.core.service.InsuranceCaseUseCaseService;
import ee.qrental.insurance.core.validator.InsuranceCaseAddBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class InsuranceCaseServiceConfig {

  @Bean
  GetInsuranceCaseQuery getInsuranceCaseQueryService(
      final InsuranceCaseLoadPort loadPort,
      final InsuranceCaseResponseMapper mapper,
      final InsuranceCaseUpdateRequestMapper updateRequestMapper) {
    return new InsuranceCaseQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  InsuranceCaseUseCaseService getInsuranceCaseUseCaseService(
      final InsuranceCaseAddPort addPort,
      final InsuranceCaseUpdatePort updatePort,
      final InsuranceCaseDeletePort deletePort,
      final InsuranceCaseLoadPort loadPort,
      final InsuranceCaseAddRequestMapper addRequestMapper,
      final InsuranceCaseUpdateRequestMapper updateRequestMapper,
      final InsuranceCaseAddBusinessRuleValidator businessRuleValidator) {
    return new InsuranceCaseUseCaseService(
        addPort,
        updatePort,
        deletePort,
        loadPort,
        addRequestMapper,
        updateRequestMapper,
        businessRuleValidator);
  }
}
