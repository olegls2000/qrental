package ee.qrental.driver.spring.config;

import ee.qrental.driver.adapter.repository.CallSignLinkRepository;
import ee.qrental.driver.repository.impl.CallSignLinkRepositoryImpl;
import ee.qrental.driver.repository.spring.CallSignLinkSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallSignLinkRepositoryConfig {

  @Bean
  CallSignLinkRepository getCallSignLinkRepository(
      final CallSignLinkSpringDataRepository springDataRepository) {
    return new CallSignLinkRepositoryImpl(springDataRepository);
  }
}
