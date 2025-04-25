package ee.qrent.billing.firm.config.spring;

import ee.qrent.billing.firm.persistence.repository.FirmRepository;
import ee.qrent.billing.firm.persistence.repository.impl.FirmRepositoryImpl;
import ee.qrent.billing.firm.persistence.repository.spring.FirmSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirmRepositoryConfig {

  @Bean
  FirmRepository getFirmRepository(final FirmSpringDataRepository springDataRepository) {
    return new FirmRepositoryImpl(springDataRepository);
  }
}
