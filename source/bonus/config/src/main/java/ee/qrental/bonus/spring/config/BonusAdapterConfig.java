package ee.qrental.bonus.spring.config;

import ee.qrental.bonus.adapter.adapter.ObligationCalculationPersistenceAdapter;
import ee.qrental.bonus.adapter.adapter.ObligationLoadAdapter;
import ee.qrental.bonus.adapter.mapper.ObligationAdapterMapper;
import ee.qrental.bonus.adapter.repository.ObligationCalculationRepository;
import ee.qrental.bonus.adapter.repository.ObligationCalculationResultRepository;
import ee.qrental.bonus.adapter.repository.ObligationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BonusAdapterConfig {
  @Bean
  ObligationAdapterMapper getFirmAdapterMapper() {
    return new ObligationAdapterMapper();
  }

  @Bean
  ObligationLoadAdapter getFirmLoadAdapter(
          final ObligationRepository repository, final ObligationAdapterMapper mapper) {
    return new ObligationLoadAdapter(repository, mapper);
  }

  @Bean
  ObligationCalculationPersistenceAdapter getObligationCalculationPersistenceAdapter(
          final ObligationCalculationRepository calculationRepository,
   final ObligationCalculationResultRepository resultRepository) {
    return new ObligationCalculationPersistenceAdapter(calculationRepository, resultRepository);
  }
}
