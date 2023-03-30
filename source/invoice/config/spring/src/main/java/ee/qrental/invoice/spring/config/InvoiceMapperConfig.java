package ee.qrental.invoice.spring.config;

import ee.qrental.invoice.core.mapper.InvoiceAddRequestMapper;
import ee.qrental.invoice.core.mapper.InvoiceResponseMapper;
import ee.qrental.invoice.core.mapper.InvoiceUpdateRequestMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoiceMapperConfig {
  @Bean
  InvoiceAddRequestMapper getInvoiceAddRequestMapper() {
    return new InvoiceAddRequestMapper();
  }

  @Bean
  InvoiceResponseMapper getInvoiceResponseMapper() {
    return new InvoiceResponseMapper();
  }

  @Bean
  InvoiceUpdateRequestMapper getInvoiceUpdateRequestMapper() {
    return new InvoiceUpdateRequestMapper();
  }
}
