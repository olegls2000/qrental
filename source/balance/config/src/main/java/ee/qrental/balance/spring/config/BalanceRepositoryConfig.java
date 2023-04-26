package ee.qrental.balance.spring.config;

import ee.qrental.balance.adapter.repository.*;
import ee.qrental.balance.repository.impl.BalanceCalculationRepositoryImpl;
import ee.qrental.balance.repository.impl.BalanceCalculationResultRepositoryImpl;
import ee.qrental.balance.repository.impl.BalanceRepositoryImpl;
import ee.qrental.balance.repository.impl.BalanceTransactionRepositoryImpl;
import ee.qrental.balance.repository.spring.BalanceCalculationResultSpringDataRepository;
import ee.qrental.balance.repository.spring.BalanceCalculationSpringDataRepository;
import ee.qrental.balance.repository.spring.BalanceSpringDataRepository;
import ee.qrental.balance.repository.spring.BalanceTransactionSpringDataRepository;
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
