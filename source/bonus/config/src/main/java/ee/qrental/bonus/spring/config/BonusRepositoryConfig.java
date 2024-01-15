package ee.qrental.bonus.spring.config;

import ee.qrental.bonus.adapter.repository.ObligationCalculationRepository;
import ee.qrental.bonus.adapter.repository.ObligationCalculationResultRepository;
import ee.qrental.bonus.repository.impl.ObligationCalculationRepositoryImpl;
import ee.qrental.bonus.repository.impl.ObligationCalculationResultRepositoryImpl;
import ee.qrental.bonus.repository.spring.ObligationCalculationResultSpringDataRepository;
import ee.qrental.bonus.repository.spring.ObligationCalculationSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BonusRepositoryConfig {

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
