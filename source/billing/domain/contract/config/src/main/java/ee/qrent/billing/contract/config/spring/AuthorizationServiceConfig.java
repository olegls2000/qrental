package ee.qrent.billing.contract.config.spring;

import ee.qrent.billing.contract.api.in.query.GetAuthorizationQuery;
import ee.qrent.billing.contract.api.in.usecase.AuthorizationPdfUseCase;
import ee.qrent.billing.contract.api.in.usecase.AuthorizationSendByEmailUseCase;
import ee.qrent.billing.contract.api.out.AuthorizationBoltAddPort;
import ee.qrent.billing.contract.api.out.AuthorizationBoltDeletePort;
import ee.qrent.billing.contract.api.out.AuthorizationBoltUpdatePort;
import ee.qrent.billing.contract.api.out.AuthorizationLoadPort;
import ee.qrent.billing.contract.core.mapper.AuthorizationAddRequestMapper;
import ee.qrent.billing.contract.core.mapper.AuthorizationResponseMapper;
import ee.qrent.billing.contract.core.mapper.AuthorizationUpdateRequestMapper;
import ee.qrent.billing.contract.core.service.AuthorizationPdfUseCaseImpl;
import ee.qrent.billing.contract.core.service.AuthorizationQueryService;
import ee.qrent.billing.contract.core.service.AuthorizationSendByEmailService;
import ee.qrent.billing.contract.core.service.AuthorizationUseCaseService;
import ee.qrent.billing.contract.core.service.pdf.AuthorizationToPdfConverter;
import ee.qrent.billing.contract.core.service.pdf.AuthorizationToPdfModelMapper;
import ee.qrent.billing.contract.core.validator.AuthorizationAddRequestValidator;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationServiceConfig {

  @Bean
  GetAuthorizationQuery getGetAuthorizationBoltQuery(
      final AuthorizationLoadPort loadPort,
      final AuthorizationResponseMapper mapper,
      final AuthorizationUpdateRequestMapper updateRequestMapper) {

    return new AuthorizationQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  AuthorizationUseCaseService getAuthorizationBoltUseCaseService(
      final AuthorizationBoltAddPort addPort,
      final AuthorizationBoltUpdatePort updatePort,
      final AuthorizationBoltDeletePort deletePort,
      final AuthorizationLoadPort loadPort,
      final AuthorizationAddRequestMapper addRequestMapper,
      final AuthorizationUpdateRequestMapper updateRequestMapper,
      final AuthorizationAddRequestValidator addRequestValidator) {

    return new AuthorizationUseCaseService(
        addPort,
        updatePort,
        deletePort,
        loadPort,
        addRequestMapper,
        updateRequestMapper,
        addRequestValidator);
  }

  @Bean
  AuthorizationToPdfConverter getAuthorizationBoltToPdfConverter() {

    return new AuthorizationToPdfConverter();
  }

  @Bean
  AuthorizationToPdfModelMapper getAuthorizationBoltToPdfModelMapper() {

    return new AuthorizationToPdfModelMapper();
  }

  @Bean
  AuthorizationSendByEmailUseCase getAuthorizationBoltSendByEmailUseCase(
      final QueueEntryPushUseCase notificationQueuePushUseCase,
      final AuthorizationLoadPort loadPort,
      final AuthorizationPdfUseCase pdfUseCase,
      final QDateTime qDateTime) {

    return new AuthorizationSendByEmailService(
        notificationQueuePushUseCase, loadPort, pdfUseCase, qDateTime);
  }

  @Bean
  AuthorizationPdfUseCase getAuthorizationBoltPdfUseCase(
      final AuthorizationLoadPort loadPort,
      final AuthorizationToPdfConverter converter,
      final AuthorizationToPdfModelMapper mapper) {

    return new AuthorizationPdfUseCaseImpl(loadPort, converter, mapper);
  }
}
