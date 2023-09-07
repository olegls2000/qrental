package ee.qrental.user.spring.config;

import ee.qrental.email.api.in.usecase.EmailSendUseCase;
import ee.qrental.user.api.in.query.GetUserAccountQuery;
import ee.qrental.user.api.out.UserAccountAddPort;
import ee.qrental.user.api.out.UserAccountDeletePort;
import ee.qrental.user.api.out.UserAccountLoadPort;
import ee.qrental.user.api.out.UserAccountUpdatePort;
import ee.qrental.user.core.mapper.UserAccountAddRequestMapper;
import ee.qrental.user.core.mapper.UserAccountResponseMapper;
import ee.qrental.user.core.mapper.UserAccountUpdateRequestMapper;
import ee.qrental.user.core.service.UserAccountQueryService;
import ee.qrental.user.core.service.UserAccountUseCaseService;
import ee.qrental.user.core.validator.UserAccountBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserAccountServiceConfig {

  @Bean
  GetUserAccountQuery getUserAccountQueryService(
      final UserAccountLoadPort loadPort,
      final UserAccountResponseMapper mapper,
      final UserAccountUpdateRequestMapper updateRequestMapper) {
    return new UserAccountQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  public UserAccountUseCaseService getUserAccountUseCaseService(
      final UserAccountAddPort addPort,
      final UserAccountUpdatePort updatePort,
      final UserAccountDeletePort deletePort,
      final UserAccountLoadPort loadPort,
      final UserAccountAddRequestMapper addRequestMapper,
      final UserAccountUpdateRequestMapper updateRequestMapper,
      final UserAccountBusinessRuleValidator businessRuleValidator,
      final EmailSendUseCase emailSendUseCase) {
    return new UserAccountUseCaseService(
        addPort, 
            updatePort, 
            deletePort, 
            loadPort, 
            addRequestMapper, 
            updateRequestMapper,
            businessRuleValidator,
            emailSendUseCase);
  }
}
