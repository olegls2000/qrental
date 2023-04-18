package ee.qrental.firm.spring.config;

import ee.qrental.constant.core.mapper.ConstantAddRequestMapper;
import ee.qrental.constant.core.mapper.ConstantResponseMapper;
import ee.qrental.constant.core.mapper.ConstantUpdateRequestMapper;
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
