package ee.qrental.car.spring.config;

import ee.qrental.car.adapter.adapter.CarLinkLoadAdapter;
import ee.qrental.car.adapter.adapter.CarLinkPersistenceAdapter;
import ee.qrental.car.adapter.mapper.CarLinkAdapterMapper;
import ee.qrental.car.adapter.repository.CarLinkRepository;
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
