package ee.qrental.user.spring.config;

import ee.qrental.user.adapter.repository.CallSignRepository;
import ee.qrental.user.repository.impl.CallSignRepositoryImpl;
import ee.qrental.user.repository.spring.CallSignSpringDataRepository;
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
