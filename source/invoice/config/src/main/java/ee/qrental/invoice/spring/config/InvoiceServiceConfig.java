package ee.qrental.invoice.spring.config;

import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.query.GetFirmLinkQuery;
import ee.qrental.email.api.in.usecase.EmailSendUseCase;
import ee.qrental.firm.api.in.query.GetFirmQuery;
import ee.qrental.invoice.api.in.query.GetInvoiceQuery;
import ee.qrental.invoice.api.in.usecase.InvoicePdfUseCase;
import ee.qrental.invoice.api.in.usecase.InvoiceSendByEmailUseCase;
import ee.qrental.invoice.api.out.*;
import ee.qrental.invoice.core.mapper.*;
import ee.qrental.invoice.core.service.*;
import ee.qrental.invoice.core.service.pdf.InvoiceToPdfConverter;
import ee.qrental.invoice.core.service.pdf.InvoiceToPdfModelMapper;
import ee.qrental.invoice.core.validator.InvoiceBusinessRuleValidator;
import ee.qrental.invoice.core.validator.InvoiceCalculationBusinessRuleValidator;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoiceServiceConfig {

  @Bean
  GetInvoiceQuery getInvoiceQueryService(
      final InvoiceLoadPort loadPort,
      final InvoiceResponseMapper mapper,
      final InvoiceUpdateRequestMapper updateRequestMapper) {
    return new InvoiceQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  InvoiceUseCaseService getInvoiceUseCaseService(
      final InvoiceAddPort addPort,
      final InvoiceUpdatePort updatePort,
      final InvoiceDeletePort deletePort,
      final InvoiceLoadPort loadPort,
      final InvoiceAddRequestMapper addRequestMapper,
      final InvoiceUpdateRequestMapper updateRequestMapper,
      final InvoiceBusinessRuleValidator businessRuleValidator) {
    return new InvoiceUseCaseService(
        addPort,
        updatePort,
        deletePort,
        loadPort,
        addRequestMapper,
        updateRequestMapper,
        businessRuleValidator);
  }

  @Bean
  InvoiceToPdfConverter getInvoiceToPdfConverter() {
    return new InvoiceToPdfConverter();
  }

  @Bean
  InvoiceToPdfModelMapper getInvoiceToPdfModelMapper() {
    return new InvoiceToPdfModelMapper();
  }

  @Bean
  InvoiceCalculationQueryService getInvoiceCalculationQueryService(
      final InvoiceCalculationLoadPort loadPort,
      final InvoiceCalculationResponseMapper responseMapper) {
    return new InvoiceCalculationQueryService(loadPort, responseMapper);
  }

  @Bean
  InvoiceCalculationService getInvoiceCalculationService(
          final InvoiceCalculationAddRequestMapper invoiceCalculationAddRequestMapper,
          final InvoiceCalculationBusinessRuleValidator invoiceCalculationBusinessRuleValidator,
          final InvoiceCalculationAddPort invoiceCalculationAddPort,
          final InvoiceCalculationPeriodService invoiceCalculationPeriodService,
          final InvoiceToPdfConverter invoiceToPdfConverter,
          final InvoiceToPdfModelMapper invoiceToPdfModelMapper,
          final GetTransactionQuery transactionQuery,
          final GetDriverQuery driverQuery,
          final GetFirmQuery firmQuery,
          final EmailSendUseCase emailSendUseCase,
          final GetBalanceQuery balanceQuery,
          final GetFirmLinkQuery firmLinkQuery) {
    return new InvoiceCalculationService(
        invoiceCalculationAddRequestMapper,
        invoiceCalculationBusinessRuleValidator,
        invoiceCalculationAddPort,
        invoiceCalculationPeriodService,
        invoiceToPdfConverter,
        invoiceToPdfModelMapper,
        transactionQuery,
        driverQuery,
        firmQuery,
        balanceQuery,
        emailSendUseCase,
        firmLinkQuery);
  }

  @Bean
  InvoiceCalculationPeriodService getInvoiceCalculationPeriodService(
      final InvoiceCalculationLoadPort loadPort) {
    return new InvoiceCalculationPeriodService(loadPort);
  }

  @Bean
  InvoiceSendByEmailUseCase getInvoiceSendByEmailUseCase(
      final EmailSendUseCase emailSendUseCase,
      final InvoiceLoadPort invoiceLoadPort,
      final GetDriverQuery driverQuery,
      InvoicePdfUseCase invoicePdfUseCase) {
    return new InvoiceSendByEmailService(
        emailSendUseCase, invoiceLoadPort, invoicePdfUseCase, driverQuery);
  }

  @Bean
  InvoicePdfUseCase getInvoicePdfUseCase(
      final InvoiceLoadPort loadPort,
      final InvoiceToPdfConverter converter,
      final InvoiceToPdfModelMapper mapper) {
    return new InvoicePdfUseCaseImpl(loadPort, converter, mapper);
  }
}
