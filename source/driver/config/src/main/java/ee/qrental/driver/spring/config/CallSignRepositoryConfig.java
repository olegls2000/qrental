package ee.qrental.driver.spring.config;

import ee.qrental.driver.adapter.repository.CallSignRepository;
import ee.qrental.driver.repository.impl.CallSignRepositoryImpl;
import ee.qrental.driver.repository.spring.CallSignSpringDataRepository;
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
