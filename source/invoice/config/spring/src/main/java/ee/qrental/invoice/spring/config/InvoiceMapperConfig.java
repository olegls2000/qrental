package ee.qrental.invoice.spring.config;

import ee.qrental.callsign.api.in.query.GetCallSignLinkQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.firm.api.in.query.GetFirmQuery;
import ee.qrental.invoice.api.out.InvoiceLoadPort;
import ee.qrental.invoice.core.mapper.InvoiceAddRequestMapper;
import ee.qrental.invoice.core.mapper.InvoiceResponseMapper;
import ee.qrental.invoice.core.mapper.InvoiceUpdateRequestMapper;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoiceMapperConfig {
  @Bean
  InvoiceAddRequestMapper getInvoiceAddRequestMapper(
      final GetDriverQuery driverQuery,
      final GetTransactionQuery transactionQuery,
      final GetCallSignLinkQuery callSignLinkQuery,
      final GetFirmQuery firmQuery) {
    return new InvoiceAddRequestMapper(driverQuery, transactionQuery, callSignLinkQuery, firmQuery);
  }

  @Bean
  InvoiceResponseMapper getInvoiceResponseMapper() {
    return new InvoiceResponseMapper();
  }

  @Bean
  InvoiceUpdateRequestMapper getInvoiceUpdateRequestMapper(final InvoiceLoadPort loadPort) {
    return new InvoiceUpdateRequestMapper(loadPort);
  }
}
