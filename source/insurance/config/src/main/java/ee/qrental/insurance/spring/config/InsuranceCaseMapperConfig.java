package ee.qrental.insurance.spring.config;

import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrental.insurance.core.mapper.InsuranceCaseAddRequestMapper;
import ee.qrental.insurance.core.mapper.InsuranceCaseResponseMapper;
import ee.qrental.insurance.core.mapper.InsuranceCaseUpdateRequestMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsuranceCaseMapperConfig {
  @Bean
  InsuranceCaseAddRequestMapper getInsuranceCaseAddRequestMapper() {
    return new InsuranceCaseAddRequestMapper();
  }

  @Bean
  InsuranceCaseResponseMapper getInsuranceCaseResponseMapper(
      final GetDriverQuery driverQuery, final GetCarQuery carQuery) {
    return new InsuranceCaseResponseMapper(driverQuery, carQuery);
  }

  @Bean
  InsuranceCaseUpdateRequestMapper getInsuranceCaseUpdateRequestMapper(
      final InsuranceCaseLoadPort loadPort) {
    return new InsuranceCaseUpdateRequestMapper(loadPort);
  }
}
