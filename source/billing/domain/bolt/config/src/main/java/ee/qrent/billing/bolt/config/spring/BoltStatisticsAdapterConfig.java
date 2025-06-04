package ee.qrent.billing.bolt.config.spring;

import ee.qrent.billing.bolt.persistence.adapter.BoltOrdersCountLoadAdapter;
import ee.qrent.billing.bolt.persistence.adapter.BoltStatisticsLoadAdapter;
import ee.qrent.billing.bolt.persistence.adapter.BoltStatisticsPersistenceAdapter;
import ee.qrent.billing.bolt.persistence.mapper.BoltOrdersCountAdapterMapper;
import ee.qrent.billing.bolt.persistence.mapper.BoltStatisticsAdapterMapper;
import ee.qrent.billing.bolt.persistence.repository.BoltOrdersCountRepository;
import ee.qrent.billing.bolt.persistence.repository.BoltStatisticsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BoltStatisticsAdapterConfig {
  @Bean
  BoltStatisticsAdapterMapper getBoltStatisticsAdapterMapper() {
    return new BoltStatisticsAdapterMapper();
  }

  @Bean
  BoltOrdersCountAdapterMapper getBoltOrdersCountAdapterMapper() {

    return new BoltOrdersCountAdapterMapper();
  }

  @Bean
  BoltStatisticsLoadAdapter getBoltStatisticsLoadAdapter(
      final BoltStatisticsRepository repository, final BoltStatisticsAdapterMapper mapper) {

    return new BoltStatisticsLoadAdapter(repository, mapper);
  }

  @Bean
  BoltOrdersCountLoadAdapter getBoltOrdersCountLoadAdapter(
      final BoltOrdersCountRepository repository, final BoltOrdersCountAdapterMapper mapper) {

    return new BoltOrdersCountLoadAdapter(repository, mapper);
  }

  @Bean
  BoltStatisticsPersistenceAdapter getBoltStatisticsPersistenceAdapter(
      final BoltStatisticsRepository repository, final BoltStatisticsAdapterMapper mapper) {

    return new BoltStatisticsPersistenceAdapter(repository, mapper);
  }
}
