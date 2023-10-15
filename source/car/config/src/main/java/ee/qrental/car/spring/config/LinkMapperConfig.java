package ee.qrental.car.spring.config;

import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.car.core.mapper.CarLinkAddRequestMapper;
import ee.qrental.car.core.mapper.CarLinkResponseMapper;
import ee.qrental.car.core.mapper.CarLinkUpdateRequestMapper;
import ee.qrental.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkMapperConfig {
  @Bean
  CarLinkAddRequestMapper getCarLinkAddRequestMapper() {
    return new CarLinkAddRequestMapper();
  }

  @Bean
  CarLinkResponseMapper getCarLinkResponseMapper(
      final GetCallSignLinkQuery callSignLinkQuery,
      final GetDriverQuery driverQuery,
      final GetCarQuery carQuery) {

    return new CarLinkResponseMapper(callSignLinkQuery, driverQuery, carQuery);
  }

  @Bean
  CarLinkUpdateRequestMapper getCarLinkUpdateRequestMapper() {
    return new CarLinkUpdateRequestMapper();
  }
}
