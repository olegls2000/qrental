package ee.qrent.billing.user.config.spring;

import ee.qrent.billing.security.api.in.usecase.PasswordUseCase;
import ee.qrent.billing.user.api.in.query.GetUserAccountQuery;
import ee.qrent.billing.user.api.out.UserAccountAddPort;
import ee.qrent.billing.user.api.out.UserAccountDeletePort;
import ee.qrent.billing.user.api.out.UserAccountLoadPort;
import ee.qrent.billing.user.api.out.UserAccountUpdatePort;
import ee.qrent.billing.user.core.mapper.UserAccountAddRequestMapper;
import ee.qrent.billing.user.core.mapper.UserAccountResponseMapper;
import ee.qrent.billing.user.core.mapper.UserAccountUpdateRequestMapper;
import ee.qrent.billing.user.core.service.UserAccountQueryService;
import ee.qrent.billing.user.core.service.UserAccountUseCaseService;
import ee.qrent.billing.user.core.validator.UserAccountRequestValidator;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
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
      final UserAccountRequestValidator requestValidator,
      final PasswordUseCase passwordUseCase,
      final QueueEntryPushUseCase notificationQueuePushUseCase,
      final QDateTime qDateTime) {
    return new UserAccountUseCaseService(
        addPort,
        updatePort,
        deletePort,
        loadPort,
        addRequestMapper,
        updateRequestMapper,
        requestValidator,
        passwordUseCase,
        notificationQueuePushUseCase,
        qDateTime);
  }
}
