package ee.qrental.firm.spring.config;

import ee.qrental.constant.core.mapper.*;
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
