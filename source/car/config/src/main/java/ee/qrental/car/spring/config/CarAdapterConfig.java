package ee.qrental.car.spring.config;

import ee.qrental.car.adapter.adapter.CarLoadAdapter;
import ee.qrental.car.adapter.adapter.CarPersistenceAdapter;
import ee.qrental.car.adapter.mapper.CarAdapterMapper;
import ee.qrental.car.adapter.repository.CarRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarAdapterConfig {
  @Bean
  CarAdapterMapper getCarAdapterMapper() {
    return new CarAdapterMapper();
  }

  @Bean
  CarLoadAdapter getCarLoadAdapter(final CarRepository repository, final CarAdapterMapper mapper) {
    return new CarLoadAdapter(repository, mapper);
  }

  @Bean
  CarPersistenceAdapter getCarPersistenceAdapter(
      final CarRepository repository, final CarAdapterMapper mapper) {
    return new CarPersistenceAdapter(repository, mapper);
  }
}
