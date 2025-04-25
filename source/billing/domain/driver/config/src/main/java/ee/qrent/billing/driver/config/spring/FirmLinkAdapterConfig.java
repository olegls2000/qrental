package ee.qrent.billing.driver.config.spring;

import ee.qrent.billing.driver.persistence.adapter.FirmLinkLoadAdapter;
import ee.qrent.billing.driver.persistence.adapter.FirmLinkPersistenceAdapter;
import ee.qrent.billing.driver.persistence.mapper.FirmLinkAdapterMapper;
import ee.qrent.billing.driver.persistence.repository.FirmLinkRepository;
import ee.qrent.billing.driver.api.out.FirmLinkLoadPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirmLinkAdapterConfig {

  @Bean
  FirmLinkAdapterMapper getFirmLinkAdapterMapper() {
    return new FirmLinkAdapterMapper();
  }

  @Bean
  FirmLinkLoadPort getFirmLinkLoadAdapter(
           final FirmLinkRepository repository,
   final FirmLinkAdapterMapper mapper) {
    return new FirmLinkLoadAdapter(repository, mapper);
  }

  @Bean
  FirmLinkPersistenceAdapter getFirmLinkPersistenceAdapter(
      final FirmLinkRepository repository, final FirmLinkAdapterMapper mapper) {
    return new FirmLinkPersistenceAdapter(repository, mapper);
  }
}
