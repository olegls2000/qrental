package ee.qrental.link.spring.config;

import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.link.core.mapper.LinkAddRequestMapper;
import ee.qrental.link.core.mapper.LinkResponseMapper;
import ee.qrental.link.core.mapper.LinkUpdateRequestMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkMapperConfig {
  @Bean
  LinkAddRequestMapper getLinkAddRequestMapper() {
    return new LinkAddRequestMapper();
  }

  @Bean
  LinkResponseMapper getLinkResponseMapper(
      final GetCallSignLinkQuery callSignLinkQuery,
      final GetDriverQuery driverQuery,
      final GetCarQuery carQuery) {

    return new LinkResponseMapper(callSignLinkQuery, driverQuery, carQuery);
  }

  @Bean
  LinkUpdateRequestMapper getLinkUpdateRequestMapper() {
    return new LinkUpdateRequestMapper();
  }
}
