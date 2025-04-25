package ee.qrent.billing.driver.config.spring;

import ee.qrent.billing.driver.persistence.adapter.CallSignLoadAdapter;
import ee.qrent.billing.driver.persistence.adapter.CallSignPersistenceAdapter;
import ee.qrent.billing.driver.persistence.mapper.CallSignAdapterMapper;
import ee.qrent.billing.driver.persistence.repository.CallSignRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallSignAdapterConfig {
  @Bean
  CallSignAdapterMapper getCallSignAdapterMapper() {
    return new CallSignAdapterMapper();
  }

  @Bean
  CallSignLoadAdapter getCallSignLoadAdapter(
      final CallSignRepository repository, final CallSignAdapterMapper mapper) {
    return new CallSignLoadAdapter(repository, mapper);
  }

  @Bean
  CallSignPersistenceAdapter getCallSignPersistenceAdapter(
      final CallSignRepository repository, final CallSignAdapterMapper mapper) {
    return new CallSignPersistenceAdapter(repository, mapper);
  }
}
