package ee.qrent.billing.contract.config.spring;

import ee.qrent.billing.contract.api.out.AuthorizationLoadPort;
import ee.qrent.billing.contract.core.mapper.AuthorizationAddRequestMapper;
import ee.qrent.billing.contract.core.mapper.AuthorizationResponseMapper;
import ee.qrent.billing.contract.core.mapper.AuthorizationUpdateRequestMapper;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationMapperConfig {
  @Bean
  AuthorizationAddRequestMapper getAuthorizationAddRequestMapper(
      final GetDriverQuery driverQuery) {
    return new AuthorizationAddRequestMapper(driverQuery);
  }

  @Bean
  AuthorizationResponseMapper getAuthorizationResponseMapper() {
    return new AuthorizationResponseMapper();
  }

  @Bean
  AuthorizationUpdateRequestMapper getAuthorizationUpdateRequestMapper(
      final AuthorizationLoadPort loadPort) {
    return new AuthorizationUpdateRequestMapper(loadPort);
  }
}
