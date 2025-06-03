package ee.qrent.billing.bolt.config.spring;

import ee.qrent.billing.bolt.core.mapper.BoltStatisticsAddRequestMapper;
import ee.qrent.billing.bolt.core.mapper.BoltStatisticsResponseMapper;
import ee.qrent.billing.bolt.core.mapper.BoltStatisticsUpdateRequestMapper;
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
  BoltStatisticsUpdateRequestMapper getBoltStatisticsUpdateRequestMapper() {
    return new BoltStatisticsUpdateRequestMapper();
  }
}
