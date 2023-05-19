package ee.qrental.driver.spring.config;

import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.out.CallSignLoadPort;
import ee.qrental.driver.core.mapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallSignLinkMapperConfig {

  @Bean
  CallSignLinkAddRequestMapper getCallSignLinkAddRequestMapper(
      final CallSignLoadPort callSignLoadPort) {
    return new CallSignLinkAddRequestMapper(callSignLoadPort);
  }

  @Bean
  CallSignLinkResponseMapper getCallSignLinkResponseMapper(final GetDriverQuery driverQuery) {
    return new CallSignLinkResponseMapper(driverQuery);
  }

  @Bean
  CallSignLinkUpdateRequestMapper getCallSignLinkUpdateRequestMapper(
      final CallSignLoadPort callSignLoadPort) {
    return new CallSignLinkUpdateRequestMapper(callSignLoadPort);
  }
}
