package ee.qrent.billing.transaction.config.spring.transaction;

import ee.qrent.billing.transaction.persistence.repository.TransactionRepository;
import ee.qrent.billing.transaction.persistence.repository.impl.TransactionRepositoryImpl;
import ee.qrent.billing.transaction.persistence.repository.spring.TransactionSpringDataRepository;
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
