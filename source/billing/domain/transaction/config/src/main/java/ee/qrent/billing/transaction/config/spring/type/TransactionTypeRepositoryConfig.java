package ee.qrent.billing.transaction.config.spring.type;

import ee.qrent.billing.transaction.persistence.repository.TransactionTypeRepository;
import ee.qrent.billing.transaction.persistence.repository.impl.type.TransactionTypeRepositoryImpl;
import ee.qrent.billing.transaction.persistence.repository.spring.type.TransactionTypeSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionTypeRepositoryConfig {

  @Bean
  TransactionTypeRepository getTransactionTypeRepository(
      final TransactionTypeSpringDataRepository springDataRepository) {
    return new TransactionTypeRepositoryImpl(springDataRepository);
  }
}
