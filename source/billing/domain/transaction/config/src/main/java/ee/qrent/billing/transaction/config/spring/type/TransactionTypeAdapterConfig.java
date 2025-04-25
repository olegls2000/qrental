package ee.qrent.billing.transaction.config.spring.type;

import ee.qrent.billing.transaction.persistence.adapter.type.TransactionTypeLoadAdapter;
import ee.qrent.billing.transaction.persistence.adapter.type.TransactionTypePersistenceAdapter;
import ee.qrent.billing.transaction.persistence.mapper.kind.TransactionKindAdapterMapper;
import ee.qrent.billing.transaction.persistence.mapper.type.TransactionTypeAdapterMapper;
import ee.qrent.billing.transaction.persistence.repository.TransactionTypeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionTypeAdapterConfig {

  @Bean
  TransactionTypeAdapterMapper getTransactionTypeAdapterMapper(
      final TransactionKindAdapterMapper transactionKindAdapterMapper) {
    return new TransactionTypeAdapterMapper(transactionKindAdapterMapper);
  }

  @Bean
  TransactionTypeLoadAdapter getTransactionTypeLoadAdapter(
      final TransactionTypeRepository repository, final TransactionTypeAdapterMapper mapper) {
    return new TransactionTypeLoadAdapter(repository, mapper);
  }

  @Bean
  TransactionTypePersistenceAdapter getTransactionTypePersistenceAdapter(
      final TransactionTypeRepository repository, final TransactionTypeAdapterMapper mapper) {
    return new TransactionTypePersistenceAdapter(repository, mapper);
  }
}
