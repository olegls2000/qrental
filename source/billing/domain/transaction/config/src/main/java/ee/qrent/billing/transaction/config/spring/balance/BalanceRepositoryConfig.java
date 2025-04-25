package ee.qrent.billing.transaction.config.spring.balance;

import ee.qrent.billing.transaction.persistence.repository.balance.BalanceCalculationRepository;
import ee.qrent.billing.transaction.persistence.repository.balance.BalanceCalculationResultRepository;
import ee.qrent.billing.transaction.persistence.repository.balance.BalanceRepository;
import ee.qrent.billing.transaction.persistence.repository.balance.BalanceTransactionRepository;
import ee.qrent.billing.transaction.persistence.repository.impl.balance.BalanceCalculationRepositoryImpl;
import ee.qrent.billing.transaction.persistence.repository.impl.balance.BalanceCalculationResultRepositoryImpl;
import ee.qrent.billing.transaction.persistence.repository.impl.balance.BalanceRepositoryImpl;
import ee.qrent.billing.transaction.persistence.repository.impl.balance.BalanceTransactionRepositoryImpl;
import ee.qrent.billing.transaction.persistence.repository.spring.balance.BalanceCalculationResultSpringDataRepository;
import ee.qrent.billing.transaction.persistence.repository.spring.balance.BalanceCalculationSpringDataRepository;
import ee.qrent.billing.transaction.persistence.repository.spring.balance.BalanceSpringDataRepository;
import ee.qrent.billing.transaction.persistence.repository.spring.balance.BalanceTransactionSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BalanceRepositoryConfig {

  @Bean
  BalanceRepository getBalanceRepository(final BalanceSpringDataRepository springDataRepository) {
    return new BalanceRepositoryImpl(springDataRepository);
  }
  @Bean
  BalanceTransactionRepository getBalanceTransactionRepository(
          final BalanceTransactionSpringDataRepository springDataRepository) {
    return new BalanceTransactionRepositoryImpl(springDataRepository);
  }

  @Bean
  BalanceCalculationRepository getBalanceCalculationRepository(
      final BalanceCalculationSpringDataRepository springDataRepository) {
    return new BalanceCalculationRepositoryImpl(springDataRepository);
  }

  @Bean
  BalanceCalculationResultRepository getBalanceCalculationResultRepository(
      final BalanceCalculationResultSpringDataRepository springDataRepository) {
    return new BalanceCalculationResultRepositoryImpl(springDataRepository);
  }
}
