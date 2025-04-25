package ee.qrent.billing.driver.config.spring;

import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.driver.core.mapper.FirmLinkAddRequestMapper;
import ee.qrent.billing.driver.core.mapper.FirmLinkResponseMapper;
import ee.qrent.billing.driver.core.mapper.FirmLinkUpdateRequestMapper;
import ee.qrent.billing.firm.api.in.query.GetFirmQuery;
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
