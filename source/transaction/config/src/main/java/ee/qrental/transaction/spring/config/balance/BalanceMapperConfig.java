package ee.qrental.transaction.spring.config.balance;

import ee.qrental.transaction.adapter.mapper.balance.BalanceCalculationAdapterMapper;
import ee.qrental.transaction.core.mapper.balance.BalanceCalculationAddRequestMapper;
import ee.qrental.transaction.core.mapper.balance.BalanceCalculationResponseMapper;
import ee.qrental.transaction.core.mapper.balance.BalanceResponseMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BalanceMapperConfig {

  @Bean
  BalanceResponseMapper getBalanceResponseMapper() {
    return new BalanceResponseMapper();
  }

  @Bean
  BalanceCalculationAddRequestMapper getBalanceCalculationAddRequestMapper() {
    return new BalanceCalculationAddRequestMapper();
  }

  @Bean
  BalanceCalculationResponseMapper getBalanceCalculationResponseMapper() {
    return new BalanceCalculationResponseMapper();
  }

  @Bean
  BalanceCalculationAdapterMapper getBalanceCalculationAdapterMapper() {
    return new BalanceCalculationAdapterMapper();
  }
}
