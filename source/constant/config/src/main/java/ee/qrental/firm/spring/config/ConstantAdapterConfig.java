package ee.qrental.firm.spring.config;

import ee.qrental.constant.adapter.adapter.ConstantLoadAdapter;
import ee.qrental.constant.adapter.adapter.ConstantPersistenceAdapter;
import ee.qrental.constant.adapter.mapper.ConstantAdapterMapper;
import ee.qrental.constant.adapter.repository.ConstantRepository;
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
