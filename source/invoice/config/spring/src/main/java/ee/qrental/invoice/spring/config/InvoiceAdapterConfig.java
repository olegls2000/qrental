package ee.qrental.invoice.spring.config;

import ee.qrental.invoice.adapter.adapter.InvoiceLoadAdapter;
import ee.qrental.invoice.adapter.adapter.InvoicePersistenceAdapter;
import ee.qrental.invoice.adapter.mapper.InvoiceAdapterMapper;
import ee.qrental.invoice.adapter.repository.InvoiceItemRepository;
import ee.qrental.invoice.adapter.repository.InvoiceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoiceAdapterConfig {
  @Bean
  InvoiceAdapterMapper getInvoiceAdapterMapper() {
    return new InvoiceAdapterMapper();
  }

  @Bean
  InvoiceLoadAdapter getInvoiceLoadAdapter(
      final InvoiceRepository repository, final InvoiceAdapterMapper mapper) {
    return new InvoiceLoadAdapter(repository, mapper);
  }

  @Bean
  InvoicePersistenceAdapter getInvoicePersistenceAdapter(
      final InvoiceRepository repository,
      final InvoiceItemRepository itemRepository,
      final InvoiceAdapterMapper mapper) {
    return new InvoicePersistenceAdapter(repository, itemRepository, mapper);
  }
}
