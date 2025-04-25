package ee.qrent.billing.firm.config.spring;

import ee.qrent.billing.firm.persistence.adapter.FirmLoadAdapter;
import ee.qrent.billing.firm.persistence.adapter.FirmPersistenceAdapter;
import ee.qrent.billing.firm.persistence.mapper.FirmAdapterMapper;
import ee.qrent.billing.firm.persistence.repository.FirmRepository;
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
