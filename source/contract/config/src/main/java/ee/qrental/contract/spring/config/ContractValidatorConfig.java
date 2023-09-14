package ee.qrental.contract.spring.config;

import ee.qrental.contract.api.out.ContractLoadPort;
import ee.qrental.contract.core.validator.ContractBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContractValidatorConfig {

  @Bean
  ContractBusinessRuleValidator getContractBusinessRuleValidator(
      final ContractLoadPort contractLoadPort) {
    return new ContractBusinessRuleValidator(contractLoadPort);
  }

}
