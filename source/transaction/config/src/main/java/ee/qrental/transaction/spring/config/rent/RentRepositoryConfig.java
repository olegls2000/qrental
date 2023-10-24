package ee.qrental.transaction.spring.config.rent;

import ee.qrental.transaction.repository.impl.rent.RentCalculationRepositoryImpl;
import ee.qrental.transaction.repository.impl.rent.RentCalculationResultRepositoryImpl;
import ee.qrental.transaction.repository.spring.rent.RentCalculationResultSpringDataRepository;
import ee.qrental.transaction.repository.spring.rent.RentCalculationSpringDataRepository;
import org.springframework.context.annotation.Bean;
import ee.qrental.transaction.adapter.repository.rent.RentCalculationRepository;
import ee.qrental.transaction.adapter.repository.rent.RentCalculationResultRepository;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RentRepositoryConfig {

  @Bean
  RentCalculationRepository getRentCalculationRepository(
      final RentCalculationSpringDataRepository springDataRepository) {

    return new RentCalculationRepositoryImpl(springDataRepository);
  }

  @Bean
  RentCalculationResultRepository getRentCalculationResultRepository(
      final RentCalculationResultSpringDataRepository springDataRepository) {

    return new RentCalculationResultRepositoryImpl(springDataRepository);
  }
}
