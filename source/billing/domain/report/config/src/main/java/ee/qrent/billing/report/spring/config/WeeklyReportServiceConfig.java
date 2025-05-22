package ee.qrent.billing.report.spring.config;

import ee.qrent.billing.invoice.api.out.*;
import ee.qrent.billing.invoice.core.mapper.*;
import ee.qrent.billing.invoice.core.service.*;
import ee.qrent.billing.report.api.out.WeeklyReportCalculationLoadPort;
import ee.qrent.billing.report.api.out.WeeklyReportLoadPort;
import ee.qrent.billing.report.core.mapper.*;
import ee.qrent.billing.report.core.service.*;
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
import ee.qrent.billing.report.core.service.pdf.WeeklyReportToPdfConverter;
import ee.qrent.billing.report.core.service.pdf.WeeklyReportToPdfModelMapper;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class WeeklyReportServiceConfig {

  @Bean
  GetInvoiceQuery getInvoiceQueryService(
      final InvoiceLoadPort loadPort,
      final WeeklyReportResponseMapper mapper,
      final InvoiceUpdateRequestMapper updateRequestMapper) {
    return new InvoiceQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  WeeklyReportCalculationUseCaseService getWeeklyReportCalculationUseCaseService(
      final InvoiceAddPort addPort,
      final InvoiceUpdatePort updatePort,
      final InvoiceDeletePort deletePort,
      final InvoiceLoadPort loadPort,
      final WeeklyReportAddRequestMapper addRequestMapper,
      final InvoiceUpdateRequestMapper updateRequestMapper,
      final AddRequestValidator<InvoiceAddRequest> addRequestValidator) {
    return new WeeklyReportCalculationUseCaseService(
        addPort,
        updatePort,
        deletePort,
        loadPort,
        addRequestMapper,
        updateRequestMapper,
        addRequestValidator);
  }

  @Bean
  WeeklyReportToPdfConverter getWeeklyReportToPdfConverter() {
    return new WeeklyReportToPdfConverter();
  }

  @Bean
  WeeklyReportToPdfModelMapper getWeeklyReportToPdfModelMapper(
      final WeeklyReportLoadPort loadPort, final GetQWeekQuery qWeekQuery) {
    return new WeeklyReportToPdfModelMapper(loadPort, qWeekQuery);
  }

  @Bean
  WeeklyReportCalculationQueryService getWeeklyReportCalculationQueryService(
      final WeeklyReportCalculationLoadPort loadPort,
      final WeeklyReportCalculationResponseMapper responseMapper) {
    return new WeeklyReportCalculationQueryService(loadPort, responseMapper);
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
      final WeeklyReportCalculationAddRequestMapper addRequestMapper,
      final AddRequestValidator<InvoiceCalculationAddRequest> addRequestValidator,
      final InvoiceCalculationAddPort invoiceCalculationAddPort,
      final WeeklyReportToPdfConverter invoiceToPdfConverter,
      final WeeklyReportToPdfModelMapper invoiceToPdfModelMapper,
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
  InvoicePdfUseCase getInvoicePdfUseCase(
      final InvoiceLoadPort loadPort,
      final WeeklyReportToPdfConverter converter,
      final WeeklyReportToPdfModelMapper mapper) {

    return new InvoicePdfUseCaseImpl(loadPort, converter, mapper);
  }
}
