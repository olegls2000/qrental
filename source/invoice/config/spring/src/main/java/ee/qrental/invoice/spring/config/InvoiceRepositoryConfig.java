package ee.qrental.invoice.spring.config;

import ee.qrental.invoice.adapter.repository.*;
import ee.qrental.invoice.repository.impl.*;
import ee.qrental.invoice.repository.spring.*;
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
