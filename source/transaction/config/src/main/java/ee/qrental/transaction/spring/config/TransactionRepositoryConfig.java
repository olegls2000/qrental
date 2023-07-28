package ee.qrental.transaction.spring.config;

import ee.qrental.transaction.adapter.repository.TransactionRepository;
import ee.qrental.transaction.repository.impl.TransactionRepositoryImpl;
import ee.qrental.transaction.repository.spring.TransactionSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionRepositoryConfig {

  @Bean
  TransactionRepository getTransactionRepository(
      final TransactionSpringDataRepository springDataRepository) {
    return new TransactionRepositoryImpl(springDataRepository);
  }
}
