package ee.qrent.billing.firm.config.spring;

import ee.qrent.billing.firm.core.mapper.FirmAddRequestMapper;
import ee.qrent.billing.firm.core.mapper.FirmResponseMapper;
import ee.qrent.billing.firm.core.mapper.FirmUpdateRequestMapper;
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
