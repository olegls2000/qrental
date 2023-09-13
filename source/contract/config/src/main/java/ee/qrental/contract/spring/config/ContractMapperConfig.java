package ee.qrental.contract.spring.config;

import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.firm.api.in.query.GetFirmQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContractMapperConfig {
  @Bean
  ContractAddRequestMapper getContractAddRequestMapper(
      final GetDriverQuery driverQuery,
      final GetTransactionQuery transactionQuery,
      final GetFirmQuery firmQuery) {
    return new ContractAddRequestMapper(driverQuery, transactionQuery, firmQuery);
  }

  @Bean
  ContractResponseMapper getContractResponseMapper() {
    return new ContractResponseMapper();
  }

  @Bean
  ContractUpdateRequestMapper getContractUpdateRequestMapper(final ContractLoadPort loadPort) {
    return new ContractUpdateRequestMapper(loadPort);
  }
}
