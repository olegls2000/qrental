package ee.qrent.billing.car.config.spring;

import ee.qrent.billing.car.persistence.repository.CarLinkRepository;
import ee.qrent.billing.car.persistence.repository.impl.CarLinkRepositoryImpl;
import ee.qrent.billing.car.persistence.repository.spring.CarLinkSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarLinkRepositoryConfig {

  @Bean
  CarLinkRepository getCarLinkRepository(final CarLinkSpringDataRepository springDataRepository) {
    return new CarLinkRepositoryImpl(springDataRepository);
  }
}
