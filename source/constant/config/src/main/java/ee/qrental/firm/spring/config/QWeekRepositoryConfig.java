package ee.qrental.firm.spring.config;

import ee.qrental.constant.adapter.repository.QWeekRepository;
import ee.qrental.constant.repository.impl.QWeekRepositoryImpl;
import ee.qrental.constant.repository.spring.QWeekSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QWeekRepositoryConfig {

  @Bean
  QWeekRepository getQWeekRepository(final QWeekSpringDataRepository springDataRepository) {
    return new QWeekRepositoryImpl(springDataRepository);
  }
}
