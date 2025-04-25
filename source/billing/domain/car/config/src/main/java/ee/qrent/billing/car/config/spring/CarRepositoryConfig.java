package ee.qrent.billing.car.config.spring;

import ee.qrent.billing.car.persistence.repository.CarRepository;
import ee.qrent.billing.car.persistence.repository.impl.CarRepositoryImpl;
import ee.qrent.billing.car.persistence.repository.spring.CarSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarRepositoryConfig {

  @Bean
  CarRepository getCarRepository(final CarSpringDataRepository springDataRepository) {
    return new CarRepositoryImpl(springDataRepository);
  }
}
