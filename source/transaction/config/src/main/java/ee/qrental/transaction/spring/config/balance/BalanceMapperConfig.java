package ee.qrental.transaction.spring.config.balance;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.adapter.mapper.balance.BalanceAdapterMapper;
import ee.qrental.transaction.adapter.mapper.balance.BalanceCalculationAdapterMapper;
import ee.qrental.transaction.core.mapper.balance.BalanceCalculationAddRequestMapper;
import ee.qrental.transaction.core.mapper.balance.BalanceCalculationResponseMapper;
import ee.qrental.transaction.core.mapper.balance.BalanceResponseMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BalanceMapperConfig {

  @Bean
  BalanceResponseMapper getBalanceResponseMapper(
      final GetQWeekQuery qWeekQuery, final GetDriverQuery driverQuery) {
    return new BalanceResponseMapper(qWeekQuery, driverQuery);
  }

  @Bean
  BalanceCalculationAddRequestMapper getBalanceCalculationAddRequestMapper() {
    return new BalanceCalculationAddRequestMapper();
  }

  @Bean
  BalanceCalculationResponseMapper getBalanceCalculationResponseMapper(
      final BalanceResponseMapper balanceResponseMapper) {
    return new BalanceCalculationResponseMapper(balanceResponseMapper);
  }

  @Bean
  BalanceCalculationAdapterMapper getBalanceCalculationAdapterMapper(
      final BalanceAdapterMapper balanceAdapterMapper) {
    return new BalanceCalculationAdapterMapper(balanceAdapterMapper);
  }
}
