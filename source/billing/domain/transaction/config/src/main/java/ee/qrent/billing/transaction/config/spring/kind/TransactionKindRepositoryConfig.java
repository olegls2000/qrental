package ee.qrent.billing.transaction.config.spring.kind;

import ee.qrent.billing.transaction.persistence.repository.kind.TransactionKindRepository;
import ee.qrent.billing.transaction.persistence.repository.impl.kind.TransactionKindRepositoryImpl;
import ee.qrent.billing.transaction.persistence.repository.spring.kind.TransactionKindSpringDataRepository;
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
