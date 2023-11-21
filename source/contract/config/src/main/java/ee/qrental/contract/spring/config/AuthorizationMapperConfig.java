package ee.qrental.contract.spring.config;

import ee.qrental.contract.api.out.AuthorizationLoadPort;
import ee.qrental.contract.core.mapper.AuthorizationAddRequestMapper;
import ee.qrental.contract.core.mapper.AuthorizationResponseMapper;
import ee.qrental.contract.core.mapper.AuthorizationUpdateRequestMapper;
import ee.qrental.driver.api.in.query.GetDriverQuery;
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
