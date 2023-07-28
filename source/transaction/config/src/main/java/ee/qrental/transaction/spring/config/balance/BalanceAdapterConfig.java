package ee.qrental.transaction.spring.config.balance;

import ee.qrental.transaction.adapter.adapter.balance.BalanceCalculationLoadAdapter;
import ee.qrental.transaction.adapter.adapter.balance.BalanceCalculationPersistenceAdapter;
import ee.qrental.transaction.adapter.adapter.balance.BalanceLoadAdapter;
import ee.qrental.transaction.adapter.adapter.balance.BalancePersistenceAdapter;
import ee.qrental.transaction.adapter.mapper.balance.BalanceAdapterMapper;
import ee.qrental.transaction.adapter.mapper.balance.BalanceCalculationAdapterMapper;
import ee.qrental.transaction.adapter.repository.balance.BalanceCalculationRepository;
import ee.qrental.transaction.adapter.repository.balance.BalanceCalculationResultRepository;
import ee.qrental.transaction.adapter.repository.balance.BalanceRepository;
import ee.qrental.transaction.adapter.repository.balance.BalanceTransactionRepository;
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
      final BalanceTransactionRepository balanceTransactionRepository,
      final BalanceAdapterMapper balanceMapper) {

    return new BalanceCalculationPersistenceAdapter(
        balanceCalculationRepository,
        balanceCalculationResultRepository,
        balanceTransactionRepository,
        balanceMapper);
  }

  @Bean
  BalanceCalculationLoadAdapter getBalanceCalculationLoadAdapter(
      final BalanceCalculationRepository repository, final BalanceCalculationAdapterMapper mapper) {
    return new BalanceCalculationLoadAdapter(repository, mapper);
  }
}
