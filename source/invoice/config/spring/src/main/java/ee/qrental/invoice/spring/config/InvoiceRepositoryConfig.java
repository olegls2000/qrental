package ee.qrental.invoice.spring.config;

import ee.qrental.invoice.adapter.repository.InvoiceItemRepository;
import ee.qrental.invoice.adapter.repository.InvoiceRepository;
import ee.qrental.invoice.repository.impl.InvoiceItemRepositoryImpl;
import ee.qrental.invoice.repository.impl.InvoiceRepositoryImpl;
import ee.qrental.invoice.repository.spring.InvoiceItemSpringDataRepository;
import ee.qrental.invoice.repository.spring.InvoiceSpringDataRepository;
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
}
