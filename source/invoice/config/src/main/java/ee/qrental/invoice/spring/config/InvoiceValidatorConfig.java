package ee.qrental.invoice.spring.config;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.invoice.api.out.InvoiceCalculationLoadPort;
import ee.qrental.invoice.api.out.InvoiceLoadPort;
import ee.qrental.invoice.core.validator.InvoiceBusinessRuleValidator;
import ee.qrental.invoice.core.validator.InvoiceCalculationBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoiceValidatorConfig {

  @Bean
  InvoiceBusinessRuleValidator getInvoiceBusinessRuleValidator(
      final InvoiceLoadPort invoiceLoadPort) {
    return new InvoiceBusinessRuleValidator(invoiceLoadPort);
  }

  @Bean
  InvoiceCalculationBusinessRuleValidator getInvoiceCalculationBusinessRuleValidator(
      final GetQWeekQuery qWeekQuery, final InvoiceCalculationLoadPort loadPort) {
    return new InvoiceCalculationBusinessRuleValidator(qWeekQuery, loadPort);
  }
}
