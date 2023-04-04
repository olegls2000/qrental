package ee.qrental.invoice.spring.config;

import ee.qrental.invoice.api.in.query.GetInvoiceQuery;
import ee.qrental.invoice.api.out.InvoiceAddPort;
import ee.qrental.invoice.api.out.InvoiceDeletePort;
import ee.qrental.invoice.api.out.InvoiceLoadPort;
import ee.qrental.invoice.api.out.InvoiceUpdatePort;
import ee.qrental.invoice.core.mapper.InvoiceAddRequestMapper;
import ee.qrental.invoice.core.mapper.InvoiceResponseMapper;
import ee.qrental.invoice.core.mapper.InvoiceUpdateRequestMapper;
import ee.qrental.invoice.core.service.InvoiceQueryService;
import ee.qrental.invoice.core.service.InvoiceUseCaseService;
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
      final GetTransactionQuery transactionQuery) {
    return new InvoiceUseCaseService(
        addPort,
        updatePort,
        deletePort,
        loadPort,
        addRequestMapper,
        updateRequestMapper,
        transactionQuery);
  }
}
