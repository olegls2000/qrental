package ee.qrental.transaction.spring.config;

import ee.qrental.transaction.adapter.adapter.TransactionLoadAdapter;
import ee.qrental.transaction.adapter.adapter.TransactionPersistenceAdapter;
import ee.qrental.transaction.adapter.mapper.TransactionAdapterMapper;
import ee.qrental.transaction.adapter.mapper.TransactionTypeAdapterMapper;
import ee.qrental.transaction.adapter.repository.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionAdapterConfig {

  @Bean
  TransactionAdapterMapper getTransactionAdapterMapper(
      final TransactionTypeAdapterMapper transactionTypeAdapterMapper) {
    return new TransactionAdapterMapper(transactionTypeAdapterMapper);
  }

  @Bean
  TransactionLoadAdapter getTransactionLoadAdapter(
      final TransactionRepository repository, final TransactionAdapterMapper mapper) {
    return new TransactionLoadAdapter(repository, mapper);
  }

  @Bean
  TransactionPersistenceAdapter getTransactionPersistenceAdapter(
      final TransactionRepository repository, final TransactionAdapterMapper mapper) {
    return new TransactionPersistenceAdapter(repository, mapper);
  }
}
