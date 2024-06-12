package ee.qrental.driver.spring.config;

import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.out.*;
import ee.qrental.driver.core.mapper.DriverAddRequestMapper;
import ee.qrental.driver.core.mapper.DriverResponseMapper;
import ee.qrental.driver.core.mapper.DriverUpdateRequestMapper;
import ee.qrental.driver.core.mapper.FriendshipResponseMapper;
import ee.qrental.driver.core.service.DriverQueryService;
import ee.qrental.driver.core.service.DriverUseCaseService;
import ee.qrental.driver.core.validator.DriverUpdateBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverServiceConfig {

  @Bean
  GetDriverQuery getDriverQueryService(
      final DriverLoadPort loadPort,
      final DriverUpdateRequestMapper updateRequestMapper,
      final DriverResponseMapper mapper,
      final FriendshipLoadPort friendshipLoadPort,
      final FriendshipResponseMapper friendshipResponseMapper) {
    return new DriverQueryService(
        loadPort, updateRequestMapper, mapper, friendshipLoadPort, friendshipResponseMapper);
  }

  @Bean
  public DriverUseCaseService getDriverUseCaseService(
      final DriverAddPort addPort,
      final DriverUpdatePort updatePort,
      final DriverDeletePort deletePort,
      final DriverLoadPort loadPort,
      final DriverAddRequestMapper addRequestMapper,
      final DriverUpdateRequestMapper updateRequestMapper,
      final DriverUpdateBusinessRuleValidator updateBusinessRuleValidator) {
    return new DriverUseCaseService(
        addPort,
        updatePort,
        deletePort,
        loadPort,
        addRequestMapper,
        updateRequestMapper,
        updateBusinessRuleValidator);
  }
}
