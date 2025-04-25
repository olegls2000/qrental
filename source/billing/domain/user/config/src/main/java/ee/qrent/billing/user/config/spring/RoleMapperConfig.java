package ee.qrent.billing.user.config.spring;

import ee.qrent.billing.user.core.mapper.RoleAddRequestMapper;
import ee.qrent.billing.user.core.mapper.RoleResponseMapper;
import ee.qrent.billing.user.core.mapper.RoleUpdateRequestMapper;
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
