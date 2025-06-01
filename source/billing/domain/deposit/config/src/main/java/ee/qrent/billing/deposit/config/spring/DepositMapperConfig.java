package ee.qrent.billing.deposit.config.spring;

import ee.qrent.billing.deposit.core.mapper.DepositAddRequestMapper;
import ee.qrent.billing.deposit.core.mapper.DepositResponseMapper;
import ee.qrent.billing.deposit.core.mapper.DepositUpdateRequestMapper;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.common.in.time.QDateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DepositMapperConfig {
  @Bean
  DepositAddRequestMapper getDepositAddRequestMapper(final QDateTime qDateTime) {

    return new DepositAddRequestMapper(qDateTime);
  }

  @Bean
  DepositResponseMapper getDepositResponseMapper(final GetDriverQuery driverQuery) {

    return new DepositResponseMapper(driverQuery);
  }

  @Bean
  DepositUpdateRequestMapper getDepositUpdateRequestMapper() {

    return new DepositUpdateRequestMapper();
  }
}
