package ee.qrent.billing.invoice.spring.config;

import ee.qrent.billing.invoice.api.out.*;
import ee.qrent.billing.invoice.core.mapper.*;
import ee.qrent.billing.invoice.core.service.*;
import ee.qrent.billing.transaction.api.in.query.kind.GetTransactionKindQuery;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.driver.api.in.query.GetFirmLinkQuery;
import ee.qrent.billing.firm.api.in.query.GetFirmQuery;
import ee.qrent.billing.invoice.api.in.query.GetInvoiceQuery;
import ee.qrent.billing.invoice.api.in.request.InvoiceAddRequest;
import ee.qrent.billing.invoice.api.in.request.InvoiceCalculationAddRequest;
import ee.qrent.billing.invoice.api.in.usecase.InvoicePdfUseCase;
import ee.qrent.billing.invoice.api.in.usecase.InvoiceSendByEmailUseCase;
import ee.qrent.billing.invoice.core.service.pdf.InvoiceToPdfConverter;
import ee.qrent.billing.invoice.core.service.pdf.InvoiceToPdfModelMapper;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
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
      final AddRequestValidator<InvoiceAddRequest> addRequestValidator) {
    return new InvoiceUseCaseService(
        addPort,
        updatePort,
        deletePort,
        loadPort,
        addRequestMapper,
        updateRequestMapper,
        addRequestValidator);
  }

  @Bean
  InvoiceToPdfConverter getInvoiceToPdfConverter() {
    return new InvoiceToPdfConverter();
  }

  @Bean
  InvoiceToPdfModelMapper getInvoiceToPdfModelMapper(
      final InvoiceLoadPort loadPort, final GetQWeekQuery qWeekQuery) {
    return new InvoiceToPdfModelMapper(loadPort, qWeekQuery);
  }

  @Bean
  InvoiceCalculationQueryService getInvoiceCalculationQueryService(
      final GetQWeekQuery qWeekQuery,
      final InvoiceCalculationLoadPort loadPort,
      final InvoiceCalculationResponseMapper responseMapper) {
    return new InvoiceCalculationQueryService(qWeekQuery, loadPort, responseMapper);
  }

  @Bean
  InvoiceCalculationService getInvoiceCalculationService(
      final GetQWeekQuery qWeekQuery,
      final GetDriverQuery driverQuery,
      final GetFirmQuery firmQuery,
      final GetBalanceQuery balanceQuery,
      final GetTransactionQuery transactionQuery,
      final GetTransactionTypeQuery transactionTypeQuery,
      final GetTransactionKindQuery transactionKindQuery,
      final GetFirmLinkQuery firmLinkQuery,
      final QueueEntryPushUseCase notificationQueuePushUseCase,
      final InvoiceCalculationLoadPort loadPort,
      final InvoiceCalculationAddRequestMapper addRequestMapper,
      final AddRequestValidator<InvoiceCalculationAddRequest> addRequestValidator,
      final InvoiceCalculationAddPort invoiceCalculationAddPort,
      final InvoiceToPdfConverter invoiceToPdfConverter,
      final InvoiceToPdfModelMapper invoiceToPdfModelMapper,
      final QDateTime qDateTime) {

    return new InvoiceCalculationService(
        qWeekQuery,
        driverQuery,
        firmQuery,
        balanceQuery,
        transactionQuery,
        transactionTypeQuery,
        transactionKindQuery,
        firmLinkQuery,
        notificationQueuePushUseCase,
        loadPort,
        addRequestMapper,
        addRequestValidator,
        invoiceCalculationAddPort,
        invoiceToPdfConverter,
        invoiceToPdfModelMapper,
        qDateTime);
  }

  @Bean
  InvoiceSendByEmailUseCase getInvoiceSendByEmailUseCase(
      final QueueEntryPushUseCase notificationQueuePushUseCase,
      final InvoiceLoadPort invoiceLoadPort,
      final GetDriverQuery driverQuery,
      final InvoicePdfUseCase invoicePdfUseCase,
      final QDateTime qDateTime) {

    return new InvoiceSendByEmailService(
        notificationQueuePushUseCase, invoiceLoadPort, invoicePdfUseCase, driverQuery, qDateTime);
  }

  @Bean
  InvoicePdfUseCase getInvoicePdfUseCase(
      final InvoiceLoadPort loadPort,
      final InvoiceToPdfConverter converter,
      final InvoiceToPdfModelMapper mapper) {

    return new InvoicePdfUseCaseImpl(loadPort, converter, mapper);
  }
}
