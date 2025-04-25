package ee.qrent.billing.transaction.config.spring.balance;

import ee.qrent.billing.transaction.persistence.adapter.balance.BalanceCalculationLoadAdapter;
import ee.qrent.billing.transaction.persistence.adapter.balance.BalanceCalculationPersistenceAdapter;
import ee.qrent.billing.transaction.persistence.adapter.balance.BalanceLoadAdapter;
import ee.qrent.billing.transaction.persistence.adapter.balance.BalancePersistenceAdapter;
import ee.qrent.billing.transaction.persistence.mapper.balance.BalanceAdapterMapper;
import ee.qrent.billing.transaction.persistence.mapper.balance.BalanceCalculationAdapterMapper;
import ee.qrent.billing.transaction.persistence.repository.balance.BalanceCalculationRepository;
import ee.qrent.billing.transaction.persistence.repository.balance.BalanceCalculationResultRepository;
import ee.qrent.billing.transaction.persistence.repository.balance.BalanceRepository;
import ee.qrent.billing.transaction.persistence.repository.balance.BalanceTransactionRepository;
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
