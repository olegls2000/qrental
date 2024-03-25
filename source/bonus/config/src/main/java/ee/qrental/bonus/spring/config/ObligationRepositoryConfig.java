package ee.qrental.bonus.spring.config;

import ee.qrental.bonus.adapter.repository.*;
import ee.qrental.bonus.repository.impl.*;
import ee.qrental.bonus.repository.spring.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObligationRepositoryConfig {

  @Bean
  ObligationRepository getObligationRepository(
      final ObligationSpringDataRepository springDataRepository) {

    return new ObligationRepositoryImpl(springDataRepository);
  }

  @Bean
  ObligationCalculationRepository getObligationCalculationRepository(
      final ObligationCalculationSpringDataRepository springDataRepository) {

    return new ObligationCalculationRepositoryImpl(springDataRepository);
  }

  @Bean
  ObligationCalculationResultRepository getObligationCalculationResultRepository(
      final ObligationCalculationResultSpringDataRepository springDataRepository) {

    return new ObligationCalculationResultRepositoryImpl(springDataRepository);
  }
}
