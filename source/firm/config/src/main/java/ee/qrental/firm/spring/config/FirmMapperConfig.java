package ee.qrental.firm.spring.config;

import ee.qrental.firm.core.mapper.FirmAddRequestMapper;
import ee.qrental.firm.core.mapper.FirmResponseMapper;
import ee.qrental.firm.core.mapper.FirmUpdateRequestMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirmMapperConfig {
  @Bean
  FirmAddRequestMapper getFirmAddRequestMapper() {
    return new FirmAddRequestMapper();
  }

  @Bean
  FirmResponseMapper getFirmResponseMapper() {
    return new FirmResponseMapper();
  }

  @Bean
  FirmUpdateRequestMapper getFirmUpdateRequestMapper() {
    return new FirmUpdateRequestMapper();
  }
}
