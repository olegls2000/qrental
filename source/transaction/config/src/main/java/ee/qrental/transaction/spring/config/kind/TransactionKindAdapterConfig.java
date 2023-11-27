package ee.qrental.transaction.spring.config.kind;

import ee.qrental.transaction.adapter.adapter.kind.TransactionKindLoadAdapter;
import ee.qrental.transaction.adapter.adapter.kind.TransactionKindPersistenceAdapter;
import ee.qrental.transaction.adapter.mapper.kind.TransactionKindAdapterMapper;
import ee.qrental.transaction.adapter.repository.kind.TransactionKindRepository;
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
