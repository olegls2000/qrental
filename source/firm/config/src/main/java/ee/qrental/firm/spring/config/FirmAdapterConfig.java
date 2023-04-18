package ee.qrental.firm.spring.config;

import ee.qrental.firm.adapter.adapter.FirmLoadAdapter;
import ee.qrental.firm.adapter.adapter.FirmPersistenceAdapter;
import ee.qrental.firm.adapter.mapper.FirmAdapterMapper;
import ee.qrental.firm.adapter.repository.FirmRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirmAdapterConfig {
  @Bean
  FirmAdapterMapper getFirmAdapterMapper() {
    return new FirmAdapterMapper();
  }

  @Bean
  FirmLoadAdapter getFirmLoadAdapter(
      final FirmRepository repository, final FirmAdapterMapper mapper) {
    return new FirmLoadAdapter(repository, mapper);
  }

  @Bean
  FirmPersistenceAdapter getFirmPersistenceAdapter(
      final FirmRepository repository, final FirmAdapterMapper mapper) {
    return new FirmPersistenceAdapter(repository, mapper);
  }
}
