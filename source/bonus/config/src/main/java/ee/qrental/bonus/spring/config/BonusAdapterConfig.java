package ee.qrental.bonus.spring.config;

import ee.qrental.bonus.adapter.adapter.*;
import ee.qrental.bonus.adapter.mapper.BonusCalculationAdapterMapper;
import ee.qrental.bonus.adapter.mapper.BonusProgramAdapterMapper;
import ee.qrental.bonus.adapter.repository.*;
import ee.qrental.bonus.api.out.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BonusAdapterConfig {
  @Bean
  BonusProgramAdapterMapper getBonusProgramAdapterMapper() {
    return new BonusProgramAdapterMapper();
  }

  @Bean
  BonusProgramLoadPort getBonusProgramLoadPort(
      final BonusProgramRepository repository, final BonusProgramAdapterMapper mapper) {
    return new BonusProgramLoadAdapter(repository, mapper);
  }

  @Bean
  BonusCalculationLoadPort getBonusCalculationLoadPort(
      final BonusCalculationRepository repository, final BonusCalculationAdapterMapper mapper) {
    return new BonusCalculationLoadAdapter(repository, mapper);
  }

  @Bean
  BonusProgramPersistenceAdapter getBonusProgramPersistenceAdapter(
      final BonusProgramRepository repository, final BonusProgramAdapterMapper mapper) {
    return new BonusProgramPersistenceAdapter(repository, mapper);
  }

  @Bean
  BonusCalculationPersistenceAdapter getBonusCalculationPersistenceAdapter(
      final BonusCalculationRepository calculationRepository,
      final BonusCalculationResultRepository resultRepository,
      final BonusProgramRepository bonusProgramRepository) {
    return new BonusCalculationPersistenceAdapter(
        calculationRepository, resultRepository, bonusProgramRepository);
  }
}
