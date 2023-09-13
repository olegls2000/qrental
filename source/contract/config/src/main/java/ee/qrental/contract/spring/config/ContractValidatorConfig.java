package ee.qrental.contract.spring.config;

import ee.qrental.invoice.api.out.ContractCalculationLoadPort;
import ee.qrental.invoice.api.out.ContractLoadPort;
import ee.qrental.invoice.core.validator.ContractBusinessRuleValidator;
import ee.qrental.invoice.core.validator.ContractCalculationBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContractValidatorConfig {

  @Bean
  ContractBusinessRuleValidator getContractBusinessRuleValidator(
      final ContractLoadPort invoiceLoadPort) {
    return new ContractBusinessRuleValidator(invoiceLoadPort);
  }

}
