package ee.qrental.driver.spring.config;

import ee.qrental.driver.adapter.repository.DriverRepository;
import ee.qrental.driver.repository.impl.DriverRepositoryImpl;
import ee.qrental.driver.repository.spring.DriverSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverRepositoryConfig {

  @Bean
  DriverRepository getDriverRepository(final DriverSpringDataRepository springDataRepository) {
    return new DriverRepositoryImpl(springDataRepository);
  }
}
