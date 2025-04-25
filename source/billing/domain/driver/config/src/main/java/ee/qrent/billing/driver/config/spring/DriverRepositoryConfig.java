package ee.qrent.billing.driver.config.spring;

import ee.qrent.billing.driver.persistence.repository.DriverRepository;
import ee.qrent.billing.driver.persistence.repository.impl.DriverRepositoryImpl;
import ee.qrent.billing.driver.persistence.repository.spring.DriverSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverRepositoryConfig {

  @Bean
  DriverRepository getDriverRepository(final DriverSpringDataRepository springDataRepository) {
    return new DriverRepositoryImpl(springDataRepository);
  }
}
