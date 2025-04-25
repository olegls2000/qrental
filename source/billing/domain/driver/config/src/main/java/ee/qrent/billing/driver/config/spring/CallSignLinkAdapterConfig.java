package ee.qrent.billing.driver.config.spring;

import ee.qrent.billing.driver.persistence.adapter.CallSignLinkLoadAdapter;
import ee.qrent.billing.driver.persistence.adapter.CallSignLinkPersistenceAdapter;
import ee.qrent.billing.driver.persistence.mapper.CallSignAdapterMapper;
import ee.qrent.billing.driver.persistence.mapper.CallSignLinkAdapterMapper;
import ee.qrent.billing.driver.persistence.repository.CallSignLinkRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallSignLinkAdapterConfig {

  @Bean
  CallSignLinkAdapterMapper getCallSignLinkAdapterMapper(
      final CallSignAdapterMapper callSignAdapterMapper) {
    return new CallSignLinkAdapterMapper(callSignAdapterMapper);
  }

  @Bean
  CallSignLinkLoadAdapter getCallSignLinkLoadAdapter(
      final CallSignLinkRepository repository, final CallSignLinkAdapterMapper mapper) {
    return new CallSignLinkLoadAdapter(repository, mapper);
  }

  @Bean
  CallSignLinkPersistenceAdapter getCallSignLinkPersistenceAdapter(
      final CallSignLinkRepository repository, final CallSignLinkAdapterMapper mapper) {
    return new CallSignLinkPersistenceAdapter(repository, mapper);
  }
}
