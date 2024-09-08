package ee.qrental.insurance.spring.config;

import ee.qrental.insurance.adapter.adapter.*;
import ee.qrental.insurance.adapter.mapper.InsuranceCalculationAdapterMapper;
import ee.qrental.insurance.adapter.mapper.InsuranceCaseAdapterMapper;
import ee.qrental.insurance.adapter.mapper.InsuranceCaseBalanceAdapterMapper;
import ee.qrental.insurance.adapter.repository.InsuranceCalculationRepository;
import ee.qrental.insurance.adapter.repository.InsuranceCaseBalanceRepository;
import ee.qrental.insurance.adapter.repository.InsuranceCaseBalanceTransactionRepository;
import ee.qrental.insurance.adapter.repository.InsuranceCaseRepository;
import ee.qrental.insurance.api.out.InsuranceCaseBalanceLoadPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsuranceAdapterConfig {
  @Bean
  InsuranceCaseAdapterMapper getInsuranceCaseAdapterMapper() {
    return new InsuranceCaseAdapterMapper();
  }

  @Bean
  InsuranceCaseLoadAdapter getInsuranceCaseLoadAdapter(
      final InsuranceCaseRepository repository, final InsuranceCaseAdapterMapper mapper) {

    return new InsuranceCaseLoadAdapter(repository, mapper);
  }

  @Bean
  InsuranceCasePersistenceAdapter getInsuranceCasePersistenceAdapter(
      final InsuranceCaseRepository repository, final InsuranceCaseAdapterMapper mapper) {

    return new InsuranceCasePersistenceAdapter(repository, mapper);
  }

  @Bean
  InsuranceCaseBalanceLoadPort getInsuranceCaseBalanceLoadPort(
      final InsuranceCaseBalanceRepository repository,
      final InsuranceCaseBalanceAdapterMapper mapper) {

    return new InsuranceCaseBalanceLoadAdapter(repository, mapper);
  }

  @Bean
  InsuranceCalculationPersistenceAdapter getInsuranceCalculationPersistenceAdapter(
      final InsuranceCalculationRepository calculationRepository,
      final InsuranceCaseBalanceRepository balanceRepository,
      final InsuranceCaseBalanceTransactionRepository balanceTransactionRepository,
      final InsuranceCalculationAdapterMapper calculationMapper,
      final InsuranceCaseBalanceAdapterMapper balanceMapper) {

    return new InsuranceCalculationPersistenceAdapter(
        calculationRepository,
        balanceRepository,
        balanceTransactionRepository,
        calculationMapper,
        balanceMapper);
  }

  @Bean
  InsuranceCalculationLoadAdapter getInsuranceCalculationLoadAdapter(
      final InsuranceCalculationRepository repository,
      final InsuranceCalculationAdapterMapper mapper) {

    return new InsuranceCalculationLoadAdapter(repository, mapper);
  }
}
