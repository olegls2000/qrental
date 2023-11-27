package ee.qrental.transaction.spring.config.kind;

import ee.qrental.transaction.adapter.repository.kind.TransactionKindRepository;
import ee.qrental.transaction.repository.impl.kind.TransactionKindRepositoryImpl;
import ee.qrental.transaction.repository.spring.kind.TransactionKindSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionKindRepositoryConfig {

  @Bean
  TransactionKindRepository getTransactionKindRepository(
      final TransactionKindSpringDataRepository springDataRepository) {
    return new TransactionKindRepositoryImpl(springDataRepository);
  }
}
