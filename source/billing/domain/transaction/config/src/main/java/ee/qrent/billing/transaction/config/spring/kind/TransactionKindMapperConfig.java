package ee.qrent.billing.transaction.config.spring.kind;

import ee.qrent.billing.transaction.core.mapper.kind.TransactionKindAddRequestMapper;
import ee.qrent.billing.transaction.core.mapper.kind.TransactionKindResponseMapper;
import ee.qrent.billing.transaction.core.mapper.kind.TransactionKindUpdateRequestMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionKindMapperConfig {
  @Bean
  TransactionKindAddRequestMapper getTransactionKindAddRequestMapper() {
    return new TransactionKindAddRequestMapper();
  }

  @Bean
  TransactionKindResponseMapper getTransactionKindResponseMapper() {
    return new TransactionKindResponseMapper();
  }

  @Bean
  TransactionKindUpdateRequestMapper getTransactionKindUpdateRequestMapper() {
    return new TransactionKindUpdateRequestMapper();
  }
}
