package ee.qrent.billing.constant.config.spring;

import ee.qrent.billing.constant.core.mapper.ConstantAddRequestMapper;
import ee.qrent.billing.constant.core.mapper.ConstantResponseMapper;
import ee.qrent.billing.constant.core.mapper.ConstantUpdateRequestMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConstantMapperConfig {
  @Bean
  ConstantAddRequestMapper getConstantAddRequestMapper() {
    return new ConstantAddRequestMapper();
  }

  @Bean
  ConstantResponseMapper getConstantResponseMapper() {
    return new ConstantResponseMapper();
  }

  @Bean
  ConstantUpdateRequestMapper getConstantUpdateRequestMapper() {
    return new ConstantUpdateRequestMapper();
  }
}
