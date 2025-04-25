package ee.qrent.billing.invoice.spring.config;

import ee.qrent.billing.invoice.adapter.repository.*;
import ee.qrent.billing.invoice.repository.impl.*;
import ee.qrent.billing.invoice.repository.spring.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoiceRepositoryConfig {

  @Bean
  InvoiceRepository getInvoiceRepository(final InvoiceSpringDataRepository springDataRepository) {
    return new InvoiceRepositoryImpl(springDataRepository);
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
