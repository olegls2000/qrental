package ee.qrent.billing.constant.config.spring;

import ee.qrent.billing.constant.persistence.repository.QWeekRepository;
import ee.qrent.billing.constant.repository.impl.QWeekRepositoryImpl;
import ee.qrent.billing.constant.repository.spring.QWeekSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QWeekRepositoryConfig {

  @Bean
  QWeekRepository getQWeekRepository(final QWeekSpringDataRepository springDataRepository) {
    return new QWeekRepositoryImpl(springDataRepository);
  }
}
