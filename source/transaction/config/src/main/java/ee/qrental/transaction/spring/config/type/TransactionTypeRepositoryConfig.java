package ee.qrental.transaction.spring.config.type;

import ee.qrental.transaction.adapter.repository.TransactionTypeRepository;
import ee.qrental.transaction.repository.impl.TransactionTypeRepositoryImpl;
import ee.qrental.transaction.repository.spring.TransactionTypeSpringDataRepository;
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
