package ee.qrental.user.spring.config;

import ee.qrental.driver.adapter.repository.FirmLinkRepository;
import ee.qrental.user.repository.impl.FirmLinkRepositoryImpl;
import ee.qrental.user.repository.spring.FirmLinkSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirmLinkRepositoryConfig {

  @Bean
  FirmLinkRepository getFirmLinkRepository(
      final FirmLinkSpringDataRepository springDataRepository) {
    return new FirmLinkRepositoryImpl(springDataRepository);
  }
}
