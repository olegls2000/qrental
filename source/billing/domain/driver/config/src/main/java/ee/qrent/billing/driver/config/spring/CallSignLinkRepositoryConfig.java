package ee.qrent.billing.driver.config.spring;

import ee.qrent.billing.driver.persistence.repository.CallSignLinkRepository;
import ee.qrent.billing.driver.persistence.repository.impl.CallSignLinkRepositoryImpl;
import ee.qrent.billing.driver.persistence.repository.spring.CallSignLinkSpringDataRepository;
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
