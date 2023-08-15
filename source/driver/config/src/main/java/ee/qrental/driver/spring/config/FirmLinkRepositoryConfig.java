package ee.qrental.driver.spring.config;

import ee.qrental.driver.adapter.repository.CallSignLinkRepository;
import ee.qrental.driver.adapter.repository.FirmLinkRepository;
import ee.qrental.driver.repository.impl.CallSignLinkRepositoryImpl;
import ee.qrental.driver.repository.impl.FirmLinkRepositoryImpl;
import ee.qrental.driver.repository.spring.CallSignLinkSpringDataRepository;
import ee.qrental.driver.repository.spring.FirmLinkSpringDataRepository;
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
