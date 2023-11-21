package ee.qrental.contract.spring.config;

import ee.qrental.contract.api.in.query.GetAuthorizationBoltQuery;
import ee.qrental.contract.api.in.query.GetContractQuery;
import ee.qrental.contract.api.in.usecase.ContractPdfUseCase;
import ee.qrental.contract.api.in.usecase.ContractSendByEmailUseCase;
import ee.qrental.contract.api.out.*;
import ee.qrental.contract.core.mapper.*;
import ee.qrental.contract.core.service.*;
import ee.qrental.contract.core.service.pdf.ContractToPdfConverter;
import ee.qrental.contract.core.service.pdf.ContractToPdfModelMapper;
import ee.qrental.contract.core.validator.AuthorizationBoltAddBusinessRuleValidator;
import ee.qrental.contract.core.validator.ContractBusinessRuleValidator;
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
  ContractToPdfConverter getContractToPdfConverter() {
    return new ContractToPdfConverter();
  }

  @Bean
  ContractToPdfModelMapper getContractToPdfModelMapper() {
    return new ContractToPdfModelMapper();
  }

  @Bean
  ContractSendByEmailUseCase getContractSendByEmailUseCase(
      final EmailSendUseCase emailSendUseCase,
      final ContractLoadPort invoiceLoadPort,
      final ContractPdfUseCase invoicePdfUseCase) {
    return new ContractSendByEmailService(emailSendUseCase, invoiceLoadPort, invoicePdfUseCase);
  }

  @Bean
  ContractPdfUseCase getContractPdfUseCase(
      final ContractLoadPort loadPort,
      final ContractToPdfConverter converter,
      final ContractToPdfModelMapper mapper) {
    return new ContractPdfUseCaseImpl(loadPort, converter, mapper);
  }
}
