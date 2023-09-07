package ee.qrental.user.spring.config;

import ee.qrental.user.core.mapper.RoleAddRequestMapper;
import ee.qrental.user.core.mapper.RoleResponseMapper;
import ee.qrental.user.core.mapper.RoleUpdateRequestMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleMapperConfig {
  @Bean
  RoleAddRequestMapper getRoleAddRequestMapper() {
    return new RoleAddRequestMapper();
  }

  @Bean
  RoleResponseMapper getRoleResponseMapper() {
    return new RoleResponseMapper();
  }

  @Bean
  RoleUpdateRequestMapper getRoleUpdateRequestMapper() {
    return new RoleUpdateRequestMapper();
  }
}
