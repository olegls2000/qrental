package ee.qrent.billing.transaction.config.spring.rent;

import ee.qrent.billing.transaction.persistence.repository.impl.rent.RentCalculationRepositoryImpl;
import ee.qrent.billing.transaction.persistence.repository.impl.rent.RentCalculationResultRepositoryImpl;
import ee.qrent.billing.transaction.persistence.repository.spring.rent.RentCalculationResultSpringDataRepository;
import ee.qrent.billing.transaction.persistence.repository.spring.rent.RentCalculationSpringDataRepository;
import org.springframework.context.annotation.Bean;
import ee.qrent.billing.transaction.persistence.repository.rent.RentCalculationRepository;
import ee.qrent.billing.transaction.persistence.repository.rent.RentCalculationResultRepository;
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
