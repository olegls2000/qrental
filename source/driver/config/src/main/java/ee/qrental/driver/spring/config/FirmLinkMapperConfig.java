package ee.qrental.driver.spring.config;

import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.core.mapper.*;
import ee.qrental.firm.api.in.query.GetFirmQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirmLinkMapperConfig {

  @Bean
  FirmLinkAddRequestMapper getFirmLinkAddRequestMapper() {
    return new FirmLinkAddRequestMapper();
  }

  @Bean
  FirmLinkResponseMapper getFirmLinkResponseMapper(
           final GetDriverQuery driverQuery,
           final GetFirmQuery firmQuery) {
    return new FirmLinkResponseMapper(driverQuery, firmQuery);
  }

  @Bean
  FirmLinkUpdateRequestMapper getFirmLinkUpdateRequestMapper() {
    return new FirmLinkUpdateRequestMapper();
  }
}
