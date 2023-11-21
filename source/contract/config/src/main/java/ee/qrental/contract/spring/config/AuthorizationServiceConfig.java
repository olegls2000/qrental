package ee.qrental.contract.spring.config;

import ee.qrental.contract.api.in.query.GetAuthorizationBoltQuery;
import ee.qrental.contract.api.in.usecase.AuthorizationBoltPdfUseCase;
import ee.qrental.contract.api.in.usecase.AuthorizationBoltSendByEmailUseCase;
import ee.qrental.contract.api.out.*;
import ee.qrental.contract.core.mapper.*;
import ee.qrental.contract.core.service.*;
import ee.qrental.contract.core.service.pdf.AuthorizationBoltToPdfConverter;
import ee.qrental.contract.core.service.pdf.AuthorizationBoltToPdfModelMapper;
import ee.qrental.contract.core.validator.AuthorizationBoltAddBusinessRuleValidator;
import ee.qrental.email.api.in.usecase.EmailSendUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationServiceConfig {

  @Bean
  GetAuthorizationBoltQuery getGetAuthorizationBoltQuery(
      final AuthorizationBoltLoadPort loadPort,
      final AuthorizationBoltResponseMapper mapper,
      final AuthorizationBoltUpdateRequestMapper updateRequestMapper) {
    return new AuthorizationBoltQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  AuthorizationBoltUseCaseService getAuthorizationBoltUseCaseService(
      final AuthorizationBoltAddPort addPort,
      final AuthorizationBoltUpdatePort updatePort,
      final AuthorizationBoltDeletePort deletePort,
      final AuthorizationBoltLoadPort loadPort,
      final AuthorizationBoltAddRequestMapper addRequestMapper,
      final AuthorizationBoltUpdateRequestMapper updateRequestMapper,
      final AuthorizationBoltAddBusinessRuleValidator addBusinessRuleValidator) {
    return new AuthorizationBoltUseCaseService(
        addPort,
        updatePort,
        deletePort,
        loadPort,
        addRequestMapper,
        updateRequestMapper,
        addBusinessRuleValidator);
  }

  @Bean
  AuthorizationBoltToPdfConverter getAuthorizationBoltToPdfConverter() {
    return new AuthorizationBoltToPdfConverter();
  }

  @Bean
  AuthorizationBoltToPdfModelMapper getAuthorizationBoltToPdfModelMapper() {
    return new AuthorizationBoltToPdfModelMapper();
  }

  @Bean
  AuthorizationBoltSendByEmailUseCase getAuthorizationBoltSendByEmailUseCase(
      final EmailSendUseCase emailSendUseCase,
      final AuthorizationBoltLoadPort loadPort,
      final AuthorizationBoltPdfUseCase pdfUseCase) {
    return new AuthorizationBoltSendByEmailService(emailSendUseCase, loadPort, pdfUseCase);
  }

  @Bean
  AuthorizationBoltPdfUseCase getAuthorizationBoltPdfUseCase(
      final AuthorizationBoltLoadPort loadPort,
      final AuthorizationBoltToPdfConverter converter,
      final AuthorizationBoltToPdfModelMapper mapper) {
    return new AuthorizationBoltPdfUseCaseImpl(loadPort, converter, mapper);
  }
}