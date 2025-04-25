package ee.qrent.billing.bonus.config.spring;

import ee.qrent.billing.bonus.api.out.ObligationAddPort;
import ee.qrent.billing.bonus.api.out.ObligationCalculationLoadPort;
import ee.qrent.billing.bonus.api.out.ObligationLoadPort;
import ee.qrent.billing.bonus.persistence.adapter.ObligationCalculationLoadAdapter;
import ee.qrent.billing.bonus.persistence.adapter.ObligationCalculationPersistenceAdapter;
import ee.qrent.billing.bonus.persistence.adapter.ObligationLoadAdapter;
import ee.qrent.billing.bonus.persistence.adapter.ObligationPersistenceAdapter;
import ee.qrent.billing.bonus.persistence.mapper.ObligationAdapterMapper;
import ee.qrent.billing.bonus.persistence.mapper.ObligationCalculationAdapterMapper;
import ee.qrent.billing.bonus.persistence.repository.ObligationCalculationRepository;
import ee.qrent.billing.bonus.persistence.repository.ObligationCalculationResultRepository;
import ee.qrent.billing.bonus.persistence.repository.ObligationRepository;
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
