package ee.qrental.invoice.spring.config;

import ee.qrental.invoice.api.out.InvoiceLoadPort;
import ee.qrental.invoice.core.validator.InvoiceBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoiceValidatorConfig {

  @Bean
  InvoiceBusinessRuleValidator getInvoiceBusinessRuleValidator(
      final InvoiceLoadPort invoiceLoadPort) {
    return new InvoiceBusinessRuleValidator(invoiceLoadPort);
  }
}
