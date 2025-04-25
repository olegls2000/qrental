package ee.qrent.billing.user.config.spring;

import ee.qrent.billing.user.api.in.query.GetRoleQuery;
import ee.qrent.billing.user.api.out.RoleAddPort;
import ee.qrent.billing.user.api.out.RoleDeletePort;
import ee.qrent.billing.user.api.out.RoleLoadPort;
import ee.qrent.billing.user.api.out.RoleUpdatePort;
import ee.qrent.billing.user.core.mapper.RoleAddRequestMapper;
import ee.qrent.billing.user.core.mapper.RoleResponseMapper;
import ee.qrent.billing.user.core.mapper.RoleUpdateRequestMapper;
import ee.qrent.billing.user.core.service.RoleQueryService;
import ee.qrent.billing.user.core.service.RoleUseCaseService;
import ee.qrent.billing.user.core.validator.RoleRequestValidator;
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
      final RoleRequestValidator requestValidator) {

    return new RoleUseCaseService(
     addPort,
     updatePort,
     deletePort,
     loadPort,
     addRequestMapper,
     updateRequestMapper,
            requestValidator);
  }
}
