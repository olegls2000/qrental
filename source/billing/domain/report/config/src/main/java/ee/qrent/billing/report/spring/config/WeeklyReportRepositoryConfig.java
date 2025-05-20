package ee.qrent.billing.report.spring.config;

import ee.qrent.billing.invoice.adapter.repository.*;
import ee.qrent.billing.invoice.repository.impl.*;
import ee.qrent.billing.invoice.repository.spring.*;
import ee.qrent.billing.report.adapter.repository.*;
import ee.qrent.billing.report.repository.impl.*;
import ee.qrent.billing.report.repository.spring.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeeklyReportRepositoryConfig {

  @Bean
  WeeklyReportRepository getInvoiceRepository(final WeeklyReportSpringDataRepository springDataRepository) {
    return new WeeklyReportRepositoryImpl(springDataRepository);
  }

  @Bean
  InvoiceItemRepository getInvoiceItemRepository(
      final InvoiceItemSpringDataRepository springDataRepository) {
    return new InvoiceItemRepositoryImpl(springDataRepository);
  }

  @Bean
  InvoiceCalculationRepository getInvoiceCalculationRepository(
      final InvoiceCalculationSpringDataRepository springDataRepository) {
    return new InvoiceCalculationRepositoryImpl(springDataRepository);
  }

  @Bean
  InvoiceCalculationResultRepository getInvoiceCalculationResultRepository(
      final InvoiceCalculationResultSpringDataRepository springDataRepository) {
    return new InvoiceCalculationResultRepositoryImpl(springDataRepository);
  }

  @Bean
  InvoiceTransactionRepository getInvoiceTransactionRepository(
      final InvoiceTransactionSpringDataRepository springDataRepository) {
    return new InvoiceTransactionRepositoryImpl(springDataRepository);
  }
}
