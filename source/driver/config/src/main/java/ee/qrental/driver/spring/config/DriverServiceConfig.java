package ee.qrental.driver.spring.config;

import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.out.DriverAddPort;
import ee.qrental.driver.api.out.DriverDeletePort;
import ee.qrental.driver.api.out.DriverLoadPort;
import ee.qrental.driver.api.out.DriverUpdatePort;
import ee.qrental.driver.core.mapper.DriverAddRequestMapper;
import ee.qrental.driver.core.mapper.DriverResponseMapper;
import ee.qrental.driver.core.mapper.DriverUpdateRequestMapper;
import ee.qrental.driver.core.service.DriverQueryService;
import ee.qrental.driver.core.service.DriverUseCaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverServiceConfig {

  @Bean
  GetDriverQuery getDriverQueryService(
      final DriverLoadPort loadPort,
      final DriverResponseMapper mapper,
      final DriverUpdateRequestMapper updateRequestMapper) {
    return new DriverQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  public DriverUseCaseService getDriverUseCaseService(
      final DriverAddPort addPort,
      final DriverUpdatePort updatePort,
      final DriverDeletePort deletePort,
      final DriverLoadPort loadPort,
      final DriverAddRequestMapper addRequestMapper,
      final DriverUpdateRequestMapper updateRequestMapper) {
    return new DriverUseCaseService(
        addPort, updatePort, deletePort, loadPort, addRequestMapper, updateRequestMapper);
  }
}
