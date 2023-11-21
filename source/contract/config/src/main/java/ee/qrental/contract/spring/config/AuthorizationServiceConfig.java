package ee.qrental.contract.spring.config;

import ee.qrental.contract.api.in.query.GetAuthorizationQuery;
import ee.qrental.contract.api.in.usecase.AuthorizationPdfUseCase;
import ee.qrental.contract.api.in.usecase.AuthorizationSendByEmailUseCase;
import ee.qrental.contract.api.out.*;
import ee.qrental.contract.core.mapper.*;
import ee.qrental.contract.core.service.*;
import ee.qrental.contract.core.service.pdf.AuthorizationToPdfConverter;
import ee.qrental.contract.core.service.pdf.AuthorizationToPdfModelMapper;
import ee.qrental.contract.core.validator.AuthorizationAddBusinessRuleValidator;
import ee.qrental.email.api.in.usecase.EmailSendUseCase;
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
      final AuthorizationAddBusinessRuleValidator addBusinessRuleValidator) {
    return new AuthorizationUseCaseService(
        addPort,
        updatePort,
        deletePort,
        loadPort,
        addRequestMapper,
        updateRequestMapper,
        addBusinessRuleValidator);
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
      final EmailSendUseCase emailSendUseCase,
      final AuthorizationLoadPort loadPort,
      final AuthorizationPdfUseCase pdfUseCase) {
    return new AuthorizationSendByEmailService(emailSendUseCase, loadPort, pdfUseCase);
  }

  @Bean
  AuthorizationPdfUseCase getAuthorizationBoltPdfUseCase(
      final AuthorizationLoadPort loadPort,
      final AuthorizationToPdfConverter converter,
      final AuthorizationToPdfModelMapper mapper) {
    return new AuthorizationPdfUseCaseImpl(loadPort, converter, mapper);
  }
}