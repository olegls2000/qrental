package ee.qrent.billing.driver.config.spring;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.core.mapper.DriverAddRequestMapper;
import ee.qrent.billing.driver.core.mapper.DriverResponseMapper;
import ee.qrent.billing.driver.core.mapper.DriverUpdateRequestMapper;
import ee.qrent.billing.driver.core.mapper.FriendshipDomainMapper;
import ee.qrent.billing.firm.api.in.query.GetFirmQuery;
import ee.qrent.billing.insurance.api.in.query.GetQKaskoQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverMapperConfig {
  @Bean
  DriverAddRequestMapper getDriverAddRequestMapper() {
    return new DriverAddRequestMapper();
  }

  @Bean
  DriverResponseMapper getDriverResponseMapper(
      final GetFirmQuery firmQuery,
      final GetQKaskoQuery qKaskoQuery,
      final GetQWeekQuery qWeekQuery) {
    return new DriverResponseMapper(firmQuery, qKaskoQuery, qWeekQuery);
  }

  @Bean
  DriverUpdateRequestMapper getDriverUpdateRequestMapper(
      final FriendshipDomainMapper friendshipDomainMapper) {
    return new DriverUpdateRequestMapper(friendshipDomainMapper);
  }
}
