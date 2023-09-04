package ee.qrental.user.spring.config;

import ee.qrental.driver.adapter.repository.CallSignLinkRepository;
import ee.qrental.user.repository.impl.CallSignLinkRepositoryImpl;
import ee.qrental.user.repository.spring.CallSignLinkSpringDataRepository;
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
