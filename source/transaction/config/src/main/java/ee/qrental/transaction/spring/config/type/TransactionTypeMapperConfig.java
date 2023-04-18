package ee.qrental.transaction.spring.config.type;

import ee.qrental.transaction.core.mapper.type.TransactionTypeAddRequestMapper;
import ee.qrental.transaction.core.mapper.type.TransactionTypeResponseMapper;
import ee.qrental.transaction.core.mapper.type.TransactionTypeUpdateRequestMapper;
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
