package ee.qrental.car.spring.config;

import ee.qrental.car.adapter.repository.CarRepository;
import ee.qrental.car.repository.impl.CarRepositoryImpl;
import ee.qrental.car.repository.spring.CarSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarRepositoryConfig {

  @Bean
  CarRepository getCarRepository(final CarSpringDataRepository springDataRepository) {
    return new CarRepositoryImpl(springDataRepository);
  }
}
