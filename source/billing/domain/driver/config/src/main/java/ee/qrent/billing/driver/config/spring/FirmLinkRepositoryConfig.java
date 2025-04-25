package ee.qrent.billing.driver.config.spring;

import ee.qrent.billing.driver.persistence.repository.FirmLinkRepository;
import ee.qrent.billing.driver.persistence.repository.impl.FirmLinkRepositoryImpl;
import ee.qrent.billing.driver.persistence.repository.spring.FirmLinkSpringDataRepository;
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
