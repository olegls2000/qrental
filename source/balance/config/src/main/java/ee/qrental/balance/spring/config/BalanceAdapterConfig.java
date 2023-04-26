package ee.qrental.balance.spring.config;

import ee.qrental.balance.adapter.adapter.BalanceCalculationLoadAdapter;
import ee.qrental.balance.adapter.adapter.BalanceCalculationPersistenceAdapter;
import ee.qrental.balance.adapter.adapter.BalanceLoadAdapter;
import ee.qrental.balance.adapter.adapter.BalancePersistenceAdapter;
import ee.qrental.balance.adapter.mapper.BalanceAdapterMapper;
import ee.qrental.balance.adapter.mapper.BalanceCalculationAdapterMapper;
import ee.qrental.balance.adapter.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BalanceAdapterConfig {
  @Bean
  BalanceAdapterMapper getBalanceAdapterMapper() {
    return new BalanceAdapterMapper();
  }

  @Bean
  BalanceLoadAdapter getBalanceLoadAdapter(
      final BalanceRepository repository, final BalanceAdapterMapper mapper) {
    return new BalanceLoadAdapter(repository, mapper);
  }

  @Bean
  BalancePersistenceAdapter getBalancePersistenceAdapter(
      final BalanceRepository repository, final BalanceAdapterMapper mapper) {
    return new BalancePersistenceAdapter(repository, mapper);
  }

  @Bean
  BalanceCalculationPersistenceAdapter getBalanceCalculationPersistenceAdapter(
      final BalanceCalculationRepository balanceCalculationRepository,
      final BalanceCalculationResultRepository balanceCalculationResultRepository,
      final BalanceRepository balanceRepository,
      final BalanceTransactionRepository balanceTransactionRepository,
      final BalanceAdapterMapper balanceMapper) {

    return new BalanceCalculationPersistenceAdapter(
        balanceCalculationRepository,
        balanceCalculationResultRepository,
        balanceRepository,
        balanceTransactionRepository,
        balanceMapper);
  }

  @Bean
  BalanceCalculationLoadAdapter getBalanceCalculationLoadAdapter(
      final BalanceCalculationRepository repository, final BalanceCalculationAdapterMapper mapper) {
    return new BalanceCalculationLoadAdapter(repository, mapper);
  }
}
