package ee.qrental.invoice.spring.config;

import ee.qrental.callsign.api.in.query.GetCallSignLinkQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.firm.api.in.query.GetFirmQuery;
import ee.qrental.invoice.api.in.query.GetInvoiceQuery;
import ee.qrental.invoice.api.in.usecase.InvoicePdfUseCase;
import ee.qrental.invoice.api.out.*;
import ee.qrental.invoice.core.mapper.*;
import ee.qrental.invoice.core.service.*;
import ee.qrental.invoice.core.validator.InvoiceBusinessRuleValidator;
import ee.qrental.invoice.core.validator.InvoiceCalculationBusinessRuleValidator;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoiceServiceConfig {

  @Bean
  public GetInvoiceQuery getInvoiceQueryService(
      final InvoiceLoadPort loadPort,
      final InvoiceResponseMapper mapper,
      final InvoiceUpdateRequestMapper updateRequestMapper) {
    return new InvoiceQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  public InvoiceUseCaseService getInvoiceUseCaseService(
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
  public InvoicePdfUseCase getInvoicePdfService(final InvoiceLoadPort loadPort) {
    return new InvoicePdfService(loadPort);
  }

  @Bean
  public InvoiceCalculationQueryService getInvoiceCalculationQueryService(
      final InvoiceCalculationLoadPort loadPort,
      final InvoiceCalculationResponseMapper responseMapper) {
    return new InvoiceCalculationQueryService(loadPort, responseMapper);
  }

  @Bean
  public InvoiceCalculationService getInvoiceCalculationService(
      final InvoiceCalculationAddPort addPort,
      final GetTransactionQuery transactionQuery,
      final GetDriverQuery driverQuery,
      final GetCallSignLinkQuery callSignLinkQuery,
      final GetFirmQuery firmQuery,
      final InvoiceCalculationAddRequestMapper addRequestMapper,
      final InvoiceCalculationBusinessRuleValidator businessRuleValidator,
      final InvoiceCalculationPeriodService datesCalculationService) {
    return new InvoiceCalculationService(
        addPort,
        transactionQuery,
        driverQuery,
        callSignLinkQuery,
        firmQuery,
        addRequestMapper,
        businessRuleValidator,
        datesCalculationService);
  }

  @Bean
  InvoiceCalculationPeriodService getInvoiceCalculationPeriodService(
      final InvoiceCalculationLoadPort loadPort) {
    return new InvoiceCalculationPeriodService(loadPort);
  }
}
