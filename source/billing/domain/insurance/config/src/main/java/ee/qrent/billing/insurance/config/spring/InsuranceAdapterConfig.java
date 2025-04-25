package ee.qrent.billing.insurance.config.spring;

import ee.qrent.billing.insurance.persistence.adapter.*;
import ee.qrent.billing.insurance.persistence.mapper.InsuranceCalculationAdapterMapper;
import ee.qrent.billing.insurance.persistence.mapper.InsuranceCaseAdapterMapper;
import ee.qrent.billing.insurance.persistence.mapper.InsuranceCaseBalanceAdapterMapper;
import ee.qrent.billing.insurance.persistence.repository.InsuranceCalculationRepository;
import ee.qrent.billing.insurance.persistence.repository.InsuranceCaseBalanceRepository;
import ee.qrent.billing.insurance.persistence.repository.InsuranceCaseBalanceTransactionRepository;
import ee.qrent.billing.insurance.persistence.repository.InsuranceCaseRepository;
import ee.qrent.billing.insurance.api.out.InsuranceCaseBalanceLoadPort;
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
