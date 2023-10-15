package ee.qrental.car.spring.config;

import ee.qrental.car.adapter.repository.CarLinkRepository;
import ee.qrental.car.repository.impl.CarLinkRepositoryImpl;
import ee.qrental.car.repository.spring.CarLinkSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarLinkRepositoryConfig {

  @Bean
  CarLinkRepository getCarLinkRepository(final CarLinkSpringDataRepository springDataRepository) {
    return new CarLinkRepositoryImpl(springDataRepository);
  }
}
