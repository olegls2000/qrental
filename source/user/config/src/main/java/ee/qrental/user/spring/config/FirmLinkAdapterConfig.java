package ee.qrental.user.spring.config;

import ee.qrental.user.adapter.adapter.FirmLinkLoadAdapter;
import ee.qrental.user.adapter.adapter.FirmLinkPersistenceAdapter;
import ee.qrental.user.adapter.mapper.FirmLinkAdapterMapper;
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
