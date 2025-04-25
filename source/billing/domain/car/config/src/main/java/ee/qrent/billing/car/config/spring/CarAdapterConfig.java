package ee.qrent.billing.car.config.spring;

import ee.qrent.billing.car.persistence.adapter.CarLoadAdapter;
import ee.qrent.billing.car.persistence.adapter.CarPersistenceAdapter;
import ee.qrent.billing.car.persistence.mapper.CarAdapterMapper;
import ee.qrent.billing.car.persistence.repository.CarRepository;
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
