package ee.qrental.bonus.spring.config;

import ee.qrental.bonus.adapter.adapter.ObligationCalculationLoadAdapter;
import ee.qrental.bonus.adapter.adapter.ObligationCalculationPersistenceAdapter;
import ee.qrental.bonus.adapter.adapter.ObligationLoadAdapter;
import ee.qrental.bonus.adapter.adapter.ObligationPersistenceAdapter;
import ee.qrental.bonus.adapter.mapper.BonusCalculationAdapterMapper;
import ee.qrental.bonus.adapter.mapper.BonusProgramAdapterMapper;
import ee.qrental.bonus.adapter.mapper.ObligationAdapterMapper;
import ee.qrental.bonus.adapter.mapper.ObligationCalculationAdapterMapper;
import ee.qrental.bonus.adapter.repository.*;
import ee.qrental.bonus.api.out.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObligationAdapterConfig {
  @Bean
  ObligationAdapterMapper getObligationAdapterMapper() {
    return new ObligationAdapterMapper();
  }

  @Bean
  ObligationLoadPort getObligationLoadPort(
      final ObligationRepository repository, final ObligationAdapterMapper mapper) {
    return new ObligationLoadAdapter(repository, mapper);
  }

  @Bean
  ObligationCalculationLoadPort getObligationCalculationLoadPort(
      final ObligationCalculationRepository repository,
      final ObligationCalculationAdapterMapper mapper) {
    return new ObligationCalculationLoadAdapter(repository, mapper);
  }

  @Bean
  ObligationAddPort getObligationPersistenceAdapter(
      final ObligationRepository repository, final ObligationAdapterMapper mapper) {
    return new ObligationPersistenceAdapter(repository, mapper);
  }

  @Bean
  ObligationCalculationPersistenceAdapter getObligationCalculationPersistenceAdapter(
      final ObligationCalculationRepository calculationRepository,
      final ObligationCalculationResultRepository resultRepository,
      final ObligationCalculationAdapterMapper mapper) {
    return new ObligationCalculationPersistenceAdapter(
        calculationRepository, resultRepository, mapper);
  }
}
