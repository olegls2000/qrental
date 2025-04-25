package ee.qrent.billing.bonus.config.spring;

import ee.qrent.billing.bonus.persistence.repository.BonusCalculationRepository;
import ee.qrent.billing.bonus.persistence.repository.BonusCalculationResultRepository;
import ee.qrent.billing.bonus.persistence.repository.BonusProgramRepository;
import ee.qrent.billing.bonus.persistence.repository.impl.BonusCalculationRepositoryImpl;
import ee.qrent.billing.bonus.persistence.repository.impl.BonusCalculationResultRepositoryImpl;
import ee.qrent.billing.bonus.persistence.repository.impl.BonusProgramRepositoryImpl;
import ee.qrent.billing.bonus.persistence.repository.spring.BonusCalculationResultSpringDataRepository;
import ee.qrent.billing.bonus.persistence.repository.spring.BonusCalculationSpringDataRepository;
import ee.qrent.billing.bonus.persistence.repository.spring.BonusProgramSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BonusRepositoryConfig {

  @Bean
  BonusCalculationRepository getBonusCalculationRepository(
      final BonusCalculationSpringDataRepository springDataRepository) {

    return new BonusCalculationRepositoryImpl(springDataRepository);
  }

  @Bean
  BonusCalculationResultRepository getBonusCalculationResultRepository(
      final BonusCalculationResultSpringDataRepository springDataRepository) {

    return new BonusCalculationResultRepositoryImpl(springDataRepository);
  }

  @Bean
  BonusProgramRepository getBonusProgramRepository(
          final BonusProgramSpringDataRepository springDataRepository) {

    return new BonusProgramRepositoryImpl(springDataRepository);
  }
}
