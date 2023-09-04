package ee.qrental.user.spring.config;

import ee.qrental.driver.adapter.repository.DriverRepository;
import ee.qrental.user.repository.impl.DriverRepositoryImpl;
import ee.qrental.user.repository.spring.DriveSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverRepositoryConfig {

  @Bean
  DriverRepository getDriverRepository(final DriveSpringDataRepository springDataRepository) {
    return new DriverRepositoryImpl(springDataRepository);
  }
}
