package ee.qrental.user.spring.config;

import ee.qrental.user.adapter.adapter.CallSignLoadAdapter;
import ee.qrental.user.adapter.adapter.CallSignPersistenceAdapter;
import ee.qrental.user.adapter.mapper.CallSignAdapterMapper;
import ee.qrental.user.adapter.repository.CallSignRepository;
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
