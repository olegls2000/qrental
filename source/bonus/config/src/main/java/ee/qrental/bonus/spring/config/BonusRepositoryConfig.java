package ee.qrental.bonus.spring.config;

import ee.qrental.bonus.adapter.repository.*;
import ee.qrental.bonus.repository.impl.*;
import ee.qrental.bonus.repository.spring.*;
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
