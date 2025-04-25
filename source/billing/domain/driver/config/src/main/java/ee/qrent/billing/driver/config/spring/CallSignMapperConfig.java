package ee.qrent.billing.driver.config.spring;

import ee.qrent.billing.driver.core.mapper.CallSignAddRequestMapper;
import ee.qrent.billing.driver.core.mapper.CallSignResponseMapper;
import ee.qrent.billing.driver.core.mapper.CallSignUpdateRequestMapper;
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
