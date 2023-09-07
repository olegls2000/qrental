package ee.qrental.user.spring.config;

import ee.qrental.user.api.in.query.GetRoleQuery;
import ee.qrental.user.api.out.*;
import ee.qrental.user.core.mapper.*;
import ee.qrental.user.core.service.RoleQueryService;
import ee.qrental.user.core.service.RoleUseCaseService;
import ee.qrental.user.core.validator.RoleBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleServiceConfig {

  @Bean
  GetRoleQuery getRoleQueryService(
      final RoleLoadPort loadPort,
      final RoleResponseMapper mapper,
      final RoleUpdateRequestMapper updateRequestMapper) {
    return new RoleQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  public RoleUseCaseService getRoleUseCaseService(
      final RoleAddPort addPort,
      final RoleUpdatePort updatePort,
      final RoleDeletePort deletePort,
      final RoleLoadPort loadPort,
      final RoleAddRequestMapper addRequestMapper,
      final RoleUpdateRequestMapper updateRequestMapper,
      final RoleBusinessRuleValidator businessRuleValidator) {

    return new RoleUseCaseService(
     addPort,
     updatePort,
     deletePort,
     loadPort,
     addRequestMapper,
     updateRequestMapper,
     businessRuleValidator);
  }
}
