package ee.qrent.billing.bonus.config.spring;

import ee.qrent.billing.bonus.api.out.BonusCalculationLoadPort;
import ee.qrent.billing.bonus.api.out.BonusProgramLoadPort;
import ee.qrent.billing.bonus.persistence.adapter.BonusCalculationLoadAdapter;
import ee.qrent.billing.bonus.persistence.adapter.BonusCalculationPersistenceAdapter;
import ee.qrent.billing.bonus.persistence.adapter.BonusProgramLoadAdapter;
import ee.qrent.billing.bonus.persistence.adapter.BonusProgramPersistenceAdapter;
import ee.qrent.billing.bonus.persistence.repository.BonusCalculationRepository;
import ee.qrent.billing.bonus.persistence.repository.BonusCalculationResultRepository;
import ee.qrent.billing.bonus.persistence.repository.BonusProgramRepository;
import ee.qrent.billing.bonus.persistence.mapper.BonusCalculationAdapterMapper;
import ee.qrent.billing.bonus.persistence.mapper.BonusProgramAdapterMapper;
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
