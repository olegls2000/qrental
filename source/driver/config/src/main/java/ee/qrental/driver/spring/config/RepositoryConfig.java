package ee.qrental.driver.spring.config;

import ee.qrental.driver.adapter.repository.DriverRepository;
import ee.qrental.driver.repository.impl.DriverRepositoryImpl;
import ee.qrental.driver.repository.spring.DriveSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

  @Bean
  DriverRepository getDriverRepository(final DriveSpringDataRepository springDataRepository) {
    return new DriverRepositoryImpl(springDataRepository);
  }
}
