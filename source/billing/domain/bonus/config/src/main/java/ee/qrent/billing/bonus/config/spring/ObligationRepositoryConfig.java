package ee.qrent.billing.bonus.config.spring;

import ee.qrent.billing.bonus.persistence.repository.ObligationCalculationRepository;
import ee.qrent.billing.bonus.persistence.repository.ObligationCalculationResultRepository;
import ee.qrent.billing.bonus.persistence.repository.ObligationRepository;
import ee.qrent.billing.bonus.persistence.repository.impl.ObligationCalculationRepositoryImpl;
import ee.qrent.billing.bonus.persistence.repository.impl.ObligationCalculationResultRepositoryImpl;
import ee.qrent.billing.bonus.persistence.repository.impl.ObligationRepositoryImpl;
import ee.qrent.billing.bonus.persistence.repository.spring.ObligationCalculationResultSpringDataRepository;
import ee.qrent.billing.bonus.persistence.repository.spring.ObligationCalculationSpringDataRepository;
import ee.qrent.billing.bonus.persistence.repository.spring.ObligationSpringDataRepository;
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
