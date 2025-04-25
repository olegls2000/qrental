package ee.qrent.billing.car.config.spring;

import ee.qrent.billing.car.persistence.adapter.CarLinkLoadAdapter;
import ee.qrent.billing.car.persistence.adapter.CarLinkPersistenceAdapter;
import ee.qrent.billing.car.persistence.mapper.CarLinkAdapterMapper;
import ee.qrent.billing.car.persistence.repository.CarLinkRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkAdapterConfig {
  @Bean
  CarLinkAdapterMapper getCarLinkAdapterMapper() {
    return new CarLinkAdapterMapper();
  }

  @Bean
  CarLinkLoadAdapter getCarLinkLoadAdapter(
      final CarLinkRepository repository, final CarLinkAdapterMapper mapper) {
    return new CarLinkLoadAdapter(repository, mapper);
  }

  @Bean
  CarLinkPersistenceAdapter getCarLinkPersistenceAdapter(
      final CarLinkRepository repository, final CarLinkAdapterMapper mapper) {
    return new CarLinkPersistenceAdapter(repository, mapper);
  }
}
