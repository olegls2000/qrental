package ee.qrent.billing.constant.config.spring;

import ee.qrent.billing.constant.core.mapper.QWeekAddRequestMapper;
import ee.qrent.billing.constant.core.mapper.QWeekResponseMapper;
import ee.qrent.billing.constant.core.mapper.QWeekUpdateRequestMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QWeekMapperConfig {
  @Bean
  QWeekAddRequestMapper getQWeekAddRequestMapper() {
    return new QWeekAddRequestMapper();
  }

  @Bean
  QWeekResponseMapper getQWeekResponseMapper() {
    return new QWeekResponseMapper();
  }

  @Bean
  QWeekUpdateRequestMapper getQWeekUpdateRequestMapper() {
    return new QWeekUpdateRequestMapper();
  }
}
