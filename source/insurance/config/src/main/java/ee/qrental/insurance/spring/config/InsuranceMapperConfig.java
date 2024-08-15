package ee.qrental.insurance.spring.config;

import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.insurance.adapter.adapter.InsuranceCaseBalancePersistenceAdapter;
import ee.qrental.insurance.adapter.mapper.InsuranceCalculationAdapterMapper;
import ee.qrental.insurance.adapter.mapper.InsuranceCaseBalanceAdapterMapper;
import ee.qrental.insurance.adapter.repository.InsuranceCaseBalanceRepository;
import ee.qrental.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrental.insurance.core.mapper.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsuranceMapperConfig {
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

  @Bean
  InsuranceCaseBalanceAdapterMapper getInsuranceCaseBalanceAdapterMapper() {
    return new InsuranceCaseBalanceAdapterMapper();
  }

  @Bean
  InsuranceCaseBalancePersistenceAdapter getInsuranceCaseBalancePersistenceAdapter(
      final InsuranceCaseBalanceRepository repository,
      final InsuranceCaseBalanceAdapterMapper mapper) {
    return new InsuranceCaseBalancePersistenceAdapter(repository, mapper);
  }

  @Bean
  InsuranceCaseBalanceResponseMapper getInsuranceCaseBalanceResponseMapper(
      final GetQWeekQuery qWeekQuery) {
    return new InsuranceCaseBalanceResponseMapper(qWeekQuery);
  }

  @Bean
  InsuranceCalculationAdapterMapper getInsuranceCalculationAdapterMapper() {
    return new InsuranceCalculationAdapterMapper();
  }

  @Bean
  InsuranceCalculationAddRequestMapper getInsuranceCalculationAddRequestMapper() {
    return new InsuranceCalculationAddRequestMapper();
  }

  @Bean
  InsuranceCalculationResponseMapper getInsuranceCalculationResponseMapper(
      final GetQWeekQuery qWeekQuery) {
    return new InsuranceCalculationResponseMapper(qWeekQuery);
  }
}
