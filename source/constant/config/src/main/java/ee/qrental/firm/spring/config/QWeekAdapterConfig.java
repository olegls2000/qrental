package ee.qrental.firm.spring.config;

import ee.qrental.constant.adapter.adapter.QWeekLoadAdapter;
import ee.qrental.constant.adapter.adapter.QWeekPersistenceAdapter;
import ee.qrental.constant.adapter.mapper.QWeekAdapterMapper;
import ee.qrental.constant.adapter.repository.QWeekRepository;
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
