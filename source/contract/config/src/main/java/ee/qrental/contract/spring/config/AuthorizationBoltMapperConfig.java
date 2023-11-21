package ee.qrental.contract.spring.config;

import ee.qrental.contract.api.out.AuthorizationBoltLoadPort;
import ee.qrental.contract.core.mapper.AuthorizationBoltAddRequestMapper;
import ee.qrental.contract.core.mapper.AuthorizationBoltResponseMapper;
import ee.qrental.contract.core.mapper.AuthorizationBoltUpdateRequestMapper;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationBoltMapperConfig {
  @Bean
  AuthorizationBoltAddRequestMapper getAuthorizationBoltAddRequestMapper(
      final GetDriverQuery driverQuery) {
    return new AuthorizationBoltAddRequestMapper(driverQuery);
  }

  @Bean
  AuthorizationBoltResponseMapper getAuthorizationBoltResponseMapper() {
    return new AuthorizationBoltResponseMapper();
  }

  @Bean
  AuthorizationBoltUpdateRequestMapper getAuthorizationBoltUpdateRequestMapper(
      final AuthorizationBoltLoadPort loadPort) {
    return new AuthorizationBoltUpdateRequestMapper(loadPort);
  }
}
