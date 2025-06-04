package ee.qrent.billing.bolt.config.spring;

import ee.qrent.billing.bolt.persistence.repository.BoltOrdersCountRepository;
import ee.qrent.billing.bolt.persistence.repository.BoltStatisticsRepository;
import ee.qrent.billing.bolt.persistence.repository.impl.BoltOrdersCountRepositoryImpl;
import ee.qrent.billing.bolt.persistence.repository.impl.BoltStatisticsRepositoryImpl;
import ee.qrent.billing.bolt.persistence.repository.spring.BoltOrdersCountSpringDataRepository;
import ee.qrent.billing.bolt.persistence.repository.spring.BoltStatisticsSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BoltStatisticsRepositoryConfig {

  @Bean
  BoltStatisticsRepository getBoltStatisticsRepository(final BoltStatisticsSpringDataRepository springDataRepository) {
    return new BoltStatisticsRepositoryImpl(springDataRepository);
  }
  @Bean
  BoltOrdersCountRepository getBoltOrdersCountRepository(final BoltOrdersCountSpringDataRepository springDataRepository) {
    return new BoltOrdersCountRepositoryImpl(springDataRepository);
  }
}
