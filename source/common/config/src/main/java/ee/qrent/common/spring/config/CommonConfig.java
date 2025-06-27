package ee.qrent.common.spring.config;

import ee.qrent.common.in.validation.AttributeChecker;
import ee.qrent.common.core.time.QDateTimeImpl;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.core.validation.AttributeCheckerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class CommonConfig {

  @Bean
  QDateTime getQDateTime() {
    return new QDateTimeImpl();
  }

  @Bean
  AttributeChecker getAttributeCheckerImpl() {
    return new AttributeCheckerImpl();
  }
}
