package ee.qrent.billing.constant.config.spring;

import ee.qrent.billing.constant.persistence.adapter.ConstantLoadAdapter;
import ee.qrent.billing.constant.persistence.adapter.ConstantPersistenceAdapter;
import ee.qrent.billing.constant.persistence.mapper.ConstantAdapterMapper;
import ee.qrent.billing.constant.persistence.repository.ConstantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConstantAdapterConfig {
  @Bean
  ConstantAdapterMapper getConstantAdapterMapper() {
    return new ConstantAdapterMapper();
  }

  @Bean
  ConstantLoadAdapter getConstantLoadAdapter(
      final ConstantRepository repository, final ConstantAdapterMapper mapper) {
    return new ConstantLoadAdapter(repository, mapper);
  }

  @Bean
  ConstantPersistenceAdapter getConstantPersistenceAdapter(
      final ConstantRepository repository, final ConstantAdapterMapper mapper) {
    return new ConstantPersistenceAdapter(repository, mapper);
  }
}
