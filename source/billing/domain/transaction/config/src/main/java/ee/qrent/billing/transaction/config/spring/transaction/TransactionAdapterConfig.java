package ee.qrent.billing.transaction.config.spring.transaction;

import ee.qrent.billing.transaction.persistence.adapter.TransactionLoadAdapter;
import ee.qrent.billing.transaction.persistence.adapter.TransactionPersistenceAdapter;
import ee.qrent.billing.transaction.persistence.mapper.TransactionAdapterMapper;
import ee.qrent.billing.transaction.persistence.mapper.type.TransactionTypeAdapterMapper;
import ee.qrent.billing.transaction.persistence.repository.TransactionRepository;
import ee.qrent.billing.transaction.persistence.repository.balance.BalanceTransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionAdapterConfig {

  @Bean
  TransactionAdapterMapper getTransactionAdapterMapper(
      final TransactionTypeAdapterMapper transactionTypeAdapterMapper,
      final BalanceTransactionRepository balanceTransactionRepository) {
    return new TransactionAdapterMapper(transactionTypeAdapterMapper, balanceTransactionRepository);
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
