package ee.qrent.billing.driver.config.spring;

import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.driver.api.out.CallSignLoadPort;
import ee.qrent.billing.driver.core.mapper.CallSignLinkAddRequestMapper;
import ee.qrent.billing.driver.core.mapper.CallSignLinkResponseMapper;
import ee.qrent.billing.driver.core.mapper.CallSignLinkUpdateRequestMapper;
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
