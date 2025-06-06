package ee.qrent.billing.bolt.config.spring;

import ee.qrent.billing.bolt.api.out.BoltStatisticsLoadPort;
import ee.qrent.billing.bolt.core.mapper.BoltRidesCountResponseMapper;
import ee.qrent.billing.bolt.core.mapper.BoltStatisticsAddRequestMapper;
import ee.qrent.billing.bolt.core.mapper.BoltStatisticsResponseMapper;
import ee.qrent.billing.bolt.core.mapper.BoltStatisticsUpdateRequestMapper;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.common.in.time.QDateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BoltStatisticsMapperConfig {
  @Bean
  BoltStatisticsAddRequestMapper getBoltStatisticsAddRequestMapper(final QDateTime qDateTime) {

    return new BoltStatisticsAddRequestMapper(qDateTime);
  }

  @Bean
  BoltStatisticsResponseMapper getBoltStatisticsResponseMapper() {
    return new BoltStatisticsResponseMapper();
  }


  @Bean
  BoltRidesCountResponseMapper getBoltRidesCountResponseMapper(final GetDriverQuery driverQuery) {
    return new BoltRidesCountResponseMapper(driverQuery);
  }

  @Bean
  BoltStatisticsUpdateRequestMapper getBoltStatisticsUpdateRequestMapper(
      final BoltStatisticsLoadPort loadPort) {

    return new BoltStatisticsUpdateRequestMapper(loadPort);
  }
}
