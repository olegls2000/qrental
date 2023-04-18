package ee.qrental.callsign.spring.config.callsign;

import ee.qrental.callsign.core.mapper.CallSignAddRequestMapper;
import ee.qrental.callsign.core.mapper.CallSignResponseMapper;
import ee.qrental.callsign.core.mapper.CallSignUpdateRequestMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallSignMapperConfig {
  @Bean
  CallSignAddRequestMapper getCallSignAddRequestMapper() {
    return new CallSignAddRequestMapper();
  }

  @Bean
  CallSignResponseMapper getCallSignResponseMapper() {
    return new CallSignResponseMapper();
  }

  @Bean
  CallSignUpdateRequestMapper getCallSignUpdateRequestMapper() {
    return new CallSignUpdateRequestMapper();
  }
}
