package ee.qrental.callsign.spring.config.link;

import ee.qrental.callsign.api.out.CallSignLoadPort;
import ee.qrental.callsign.core.mapper.*;
import ee.qrental.driver.api.in.query.GetDriverQuery;
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
