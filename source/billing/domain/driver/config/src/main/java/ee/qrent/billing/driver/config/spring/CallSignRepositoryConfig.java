package ee.qrent.billing.driver.config.spring;

import ee.qrent.billing.driver.persistence.repository.CallSignRepository;
import ee.qrent.billing.driver.persistence.repository.impl.CallSignRepositoryImpl;
import ee.qrent.billing.driver.persistence.repository.spring.CallSignSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallSignRepositoryConfig {

  @Bean
  CallSignRepository getCallSignRepository(
      final CallSignSpringDataRepository springDataRepository) {
    return new CallSignRepositoryImpl(springDataRepository);
  }
}
