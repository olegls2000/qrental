package ee.qrental.contract.spring.config;

import ee.qrental.contract.api.out.ContractLoadPort;
import ee.qrental.contract.core.mapper.ContractAddRequestMapper;
import ee.qrental.contract.core.mapper.ContractResponseMapper;
import ee.qrental.contract.core.mapper.ContractUpdateRequestMapper;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContractMapperConfig {
  @Bean
  ContractAddRequestMapper getContractAddRequestMapper(final GetDriverQuery driverQuery) {
    return new ContractAddRequestMapper(driverQuery);
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
