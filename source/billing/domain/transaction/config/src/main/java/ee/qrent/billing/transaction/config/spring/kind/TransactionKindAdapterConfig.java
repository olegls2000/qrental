package ee.qrent.billing.transaction.config.spring.kind;

import ee.qrent.billing.transaction.persistence.adapter.kind.TransactionKindLoadAdapter;
import ee.qrent.billing.transaction.persistence.adapter.kind.TransactionKindPersistenceAdapter;
import ee.qrent.billing.transaction.persistence.mapper.kind.TransactionKindAdapterMapper;
import ee.qrent.billing.transaction.persistence.repository.kind.TransactionKindRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionKindAdapterConfig {

  @Bean
  TransactionKindAdapterMapper getTTransactionKindAdapterMapper() {
    return new TransactionKindAdapterMapper();
  }

  @Bean
  TransactionKindLoadAdapter getTransactionKindLoadAdapter(
      final TransactionKindRepository repository, final TransactionKindAdapterMapper mapper) {
    return new TransactionKindLoadAdapter(repository, mapper);
  }

  @Bean
  TransactionKindPersistenceAdapter getTransactionKindPersistenceAdapter(
      final TransactionKindRepository repository, final TransactionKindAdapterMapper mapper) {
    return new TransactionKindPersistenceAdapter(repository, mapper);
  }
}
