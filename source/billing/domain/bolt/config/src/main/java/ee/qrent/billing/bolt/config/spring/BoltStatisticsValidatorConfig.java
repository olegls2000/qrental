package ee.qrent.billing.bolt.config.spring;

import ee.qrent.billing.bolt.core.validator.BoltStatisticsRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BoltStatisticsValidatorConfig {
  @Bean
  BoltStatisticsRequestValidator getBoltStatisticsRequestValidator() {
    return new BoltStatisticsRequestValidator();
  }
}
