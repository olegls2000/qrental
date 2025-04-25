package ee.qrent.billing.invoice.spring.config;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.firm.api.in.query.GetFirmQuery;
import ee.qrent.billing.invoice.core.mapper.*;
import ee.qrent.billing.invoice.adapter.mapper.InvoiceCalculationAdapterMapper;
import ee.qrent.billing.invoice.api.out.InvoiceLoadPort;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoiceMapperConfig {
  @Bean
  InvoiceAddRequestMapper getInvoiceAddRequestMapper(
      final GetDriverQuery driverQuery,
      final GetTransactionQuery transactionQuery,
      final GetFirmQuery firmQuery) {
    return new InvoiceAddRequestMapper(driverQuery, transactionQuery, firmQuery);
  }

  @Bean
  InvoiceResponseMapper getInvoiceResponseMapper(final GetQWeekQuery qWeekQuery) {
    return new InvoiceResponseMapper(qWeekQuery);
  }

  @Bean
  InvoiceUpdateRequestMapper getInvoiceUpdateRequestMapper(final InvoiceLoadPort loadPort) {
    return new InvoiceUpdateRequestMapper(loadPort);
  }

  @Bean
  InvoiceCalculationAddRequestMapper getInvoiceCalculationAddRequestMapper() {
    return new InvoiceCalculationAddRequestMapper();
  }

  @Bean
  InvoiceCalculationResponseMapper getInvoiceCalculationResponseMapper(final GetQWeekQuery qWeekQuery) {
    return new InvoiceCalculationResponseMapper(qWeekQuery);
  }

  @Bean
  InvoiceCalculationAdapterMapper getInvoiceCalculationAdapterMapper() {
    return new InvoiceCalculationAdapterMapper();
  }
}
