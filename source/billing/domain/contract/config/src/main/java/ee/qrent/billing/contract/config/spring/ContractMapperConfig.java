package ee.qrent.billing.contract.config.spring;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.contract.api.out.ContractLoadPort;
import ee.qrent.billing.contract.core.mapper.ContractAddRequestMapper;
import ee.qrent.billing.contract.core.mapper.ContractResponseMapper;
import ee.qrent.billing.contract.core.mapper.ContractUpdateRequestMapper;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.firm.api.in.query.GetFirmQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContractMapperConfig {
  @Bean
  ContractAddRequestMapper getContractAddRequestMapper(
      final GetDriverQuery driverQuery, final GetFirmQuery firmQuery, final QDateTime qDateTime) {

    return new ContractAddRequestMapper(driverQuery, firmQuery, qDateTime);
  }

  @Bean
  ContractResponseMapper getContractResponseMapper(final QDateTime qDateTime) {

    return new ContractResponseMapper(qDateTime);
  }

  @Bean
  ContractUpdateRequestMapper getContractUpdateRequestMapper(final ContractLoadPort loadPort) {

    return new ContractUpdateRequestMapper(loadPort);
  }
}
