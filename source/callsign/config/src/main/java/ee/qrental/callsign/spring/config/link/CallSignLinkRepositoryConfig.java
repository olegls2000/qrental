package ee.qrental.callsign.spring.config.link;

import ee.qrental.callsign.adapter.repository.CallSignLinkRepository;import ee.qrental.callsign.adapter.repository.CallSignRepository;
import ee.qrental.callsign.repository.impl.CallSignLinkRepositoryImpl;
import ee.qrental.callsign.repository.impl.CallSignRepositoryImpl;
import ee.qrental.callsign.repository.spring.CallSignLinkSpringDataRepository;
import ee.qrental.callsign.repository.spring.CallSignSpringDataRepository;
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
