package ee.qrent.billing.car.config.spring;

import ee.qrent.billing.car.api.in.query.GetCarQuery;
import ee.qrent.billing.car.core.mapper.CarLinkAddRequestMapper;
import ee.qrent.billing.car.core.mapper.CarLinkResponseMapper;
import ee.qrent.billing.car.core.mapper.CarLinkUpdateRequestMapper;
import ee.qrent.billing.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
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
