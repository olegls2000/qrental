package ee.qrental.driver.spring.config;

import ee.qrental.driver.adapter.adapter.DriverLoadAdapter;
import ee.qrental.driver.adapter.adapter.DriverPersistenceAdapter;
import ee.qrental.driver.adapter.mapper.DriverAdapterMapper;
import ee.qrental.driver.adapter.repository.DriverRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdapterConfig {
  @Bean
  DriverAdapterMapper getDriverAdapterMapper() {
    return new DriverAdapterMapper();
  }

  @Bean
  DriverLoadAdapter getDriverLoadAdapter(
      final DriverRepository repository, final DriverAdapterMapper mapper) {
    return new DriverLoadAdapter(repository, mapper);
  }

  @Bean
  DriverPersistenceAdapter getDriverPersistenceAdapter(
      final DriverRepository repository, final DriverAdapterMapper mapper) {
    return new DriverPersistenceAdapter(repository, mapper);
  }
}
