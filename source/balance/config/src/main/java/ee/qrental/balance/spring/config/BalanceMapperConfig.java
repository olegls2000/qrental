package ee.qrental.balance.spring.config;

import ee.qrental.balance.adapter.mapper.BalanceCalculationAdapterMapper;
import ee.qrental.balance.core.mapper.*;
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
