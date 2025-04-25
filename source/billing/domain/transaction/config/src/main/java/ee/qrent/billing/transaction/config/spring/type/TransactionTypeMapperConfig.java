package ee.qrent.billing.transaction.config.spring.type;

import ee.qrent.billing.transaction.core.mapper.type.TransactionTypeAddRequestMapper;
import ee.qrent.billing.transaction.core.mapper.type.TransactionTypeResponseMapper;
import ee.qrent.billing.transaction.core.mapper.type.TransactionTypeUpdateRequestMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionTypeMapperConfig {
  @Bean
  TransactionTypeAddRequestMapper getTransactionTypeAddRequestMapper() {
    return new TransactionTypeAddRequestMapper();
  }

  @Bean
  TransactionTypeResponseMapper getTransactionTypeResponseMapper() {
    return new TransactionTypeResponseMapper();
  }

  @Bean
  TransactionTypeUpdateRequestMapper getTransactionTypeUpdateRequestMapper() {
    return new TransactionTypeUpdateRequestMapper();
  }
}
