package ee.qrental.callsign.spring.config;

import ee.qrental.callsign.adapter.repository.CallSignRepository;
import ee.qrental.callsign.repository.impl.CallSignRepositoryImpl;
import ee.qrental.callsign.repository.spring.CallSignSpringDataRepository;
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
