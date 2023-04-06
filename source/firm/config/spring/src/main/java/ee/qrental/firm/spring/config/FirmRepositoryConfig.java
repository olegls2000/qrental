package ee.qrental.firm.spring.config;

import ee.qrental.firm.adapter.repository.FirmRepository;
import ee.qrental.firm.repository.impl.FirmRepositoryImpl;
import ee.qrental.firm.repository.spring.FirmSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirmRepositoryConfig {

  @Bean
  FirmRepository getFirmRepository(final FirmSpringDataRepository springDataRepository) {
    return new FirmRepositoryImpl(springDataRepository);
  }
}
