package ee.qrent.billing.constant.config.spring;

import ee.qrent.billing.constant.persistence.adapter.QWeekLoadAdapter;
import ee.qrent.billing.constant.persistence.adapter.QWeekPersistenceAdapter;
import ee.qrent.billing.constant.persistence.mapper.QWeekAdapterMapper;
import ee.qrent.billing.constant.persistence.repository.QWeekRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QWeekAdapterConfig {
  @Bean
  QWeekAdapterMapper getQWeekAdapterMapper() {
    return new QWeekAdapterMapper();
  }

  @Bean
  QWeekLoadAdapter getQWeekLoadAdapter(
      final QWeekRepository repository, final QWeekAdapterMapper mapper) {
    return new QWeekLoadAdapter(repository, mapper);
  }

  @Bean
  QWeekPersistenceAdapter getQWeekPersistenceAdapter(
      final QWeekRepository repository, final QWeekAdapterMapper mapper) {
    return new QWeekPersistenceAdapter(repository, mapper);
  }
}
