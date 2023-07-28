package ee.qrental.transaction.spring.config;

import ee.qrental.transaction.adapter.adapter.TransactionLoadAdapter;
import ee.qrental.transaction.adapter.adapter.TransactionPersistenceAdapter;
import ee.qrental.transaction.adapter.mapper.TransactionAdapterMapper;
import ee.qrental.transaction.adapter.mapper.type.TransactionTypeAdapterMapper;
import ee.qrental.transaction.adapter.repository.TransactionRepository;
import ee.qrental.transaction.adapter.repository.balance.BalanceTransactionRepository;
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
