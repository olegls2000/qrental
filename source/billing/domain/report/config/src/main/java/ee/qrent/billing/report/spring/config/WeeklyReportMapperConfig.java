package ee.qrent.billing.report.spring.config;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.firm.api.in.query.GetFirmQuery;
import ee.qrent.billing.invoice.core.mapper.*;
import ee.qrent.billing.invoice.api.out.InvoiceLoadPort;
import ee.qrent.billing.report.core.mapper.*;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeeklyReportMapperConfig {
  @Bean
  WeeklyReportAddRequestMapper getInvoiceAddRequestMapper(
      final GetDriverQuery driverQuery,
      final GetTransactionQuery transactionQuery,
      final GetFirmQuery firmQuery) {
    return new WeeklyReportAddRequestMapper(driverQuery, transactionQuery, firmQuery);
  }

  @Bean
  WeeklyReportResponseMapper getInvoiceResponseMapper(final GetQWeekQuery qWeekQuery) {
    return new WeeklyReportResponseMapper(qWeekQuery);
  }

  @Bean
  InvoiceUpdateRequestMapper getInvoiceUpdateRequestMapper(final InvoiceLoadPort loadPort) {
    return new InvoiceUpdateRequestMapper(loadPort);
  }

  @Bean
  WeeklyReportCalculationAddRequestMapper getInvoiceCalculationAddRequestMapper() {
    return new WeeklyReportCalculationAddRequestMapper();
  }

  @Bean
  WeeklyReportCalculationResponseMapper getInvoiceCalculationResponseMapper(final GetQWeekQuery qWeekQuery) {
    return new WeeklyReportCalculationResponseMapper(qWeekQuery);
  }

  @Bean
  InvoiceCalculationAdapterMapper getInvoiceCalculationAdapterMapper() {
    return new InvoiceCalculationAdapterMapper();
  }
}
