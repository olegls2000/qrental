package ee.qrental.driver.spring.config;

import ee.qrental.driver.core.mapper.DriverAddRequestMapper;
import ee.qrental.driver.core.mapper.DriverResponseMapper;
import ee.qrental.driver.core.mapper.DriverUpdateRequestMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverMapperConfig {
  @Bean
  DriverAddRequestMapper getDriverAddRequestMapper() {
    return new DriverAddRequestMapper();
  }

  @Bean
  DriverResponseMapper getDriverResponseMapper() {
    return new DriverResponseMapper();
  }

  @Bean
  DriverUpdateRequestMapper getDriverUpdateRequestMapper() {
    return new DriverUpdateRequestMapper();
  }
}
