package ee.qrental.driver.spring.config;

import ee.qrental.driver.adapter.adapter.FirmLinkLoadAdapter;
import ee.qrental.driver.adapter.adapter.FirmLinkPersistenceAdapter;
import ee.qrental.driver.adapter.mapper.FirmLinkAdapterMapper;
import ee.qrental.driver.adapter.repository.FirmLinkRepository;
import ee.qrental.driver.api.out.FirmLinkLoadPort;
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
