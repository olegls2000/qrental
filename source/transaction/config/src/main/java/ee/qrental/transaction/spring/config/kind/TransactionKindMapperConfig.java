package ee.qrental.transaction.spring.config.kind;

import ee.qrental.transaction.core.mapper.kind.TransactionKindAddRequestMapper;
import ee.qrental.transaction.core.mapper.kind.TransactionKindResponseMapper;
import ee.qrental.transaction.core.mapper.kind.TransactionKindUpdateRequestMapper;
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
