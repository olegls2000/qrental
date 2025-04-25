package ee.qrent.billing.transaction.config.spring.balance;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.transaction.persistence.mapper.balance.BalanceAdapterMapper;
import ee.qrent.billing.transaction.persistence.mapper.balance.BalanceCalculationAdapterMapper;
import ee.qrent.billing.transaction.core.mapper.balance.BalanceCalculationAddRequestMapper;
import ee.qrent.billing.transaction.core.mapper.balance.BalanceCalculationResponseMapper;
import ee.qrent.billing.transaction.core.mapper.balance.BalanceResponseMapper;
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
