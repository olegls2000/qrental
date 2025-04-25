package ee.qrent.billing.transaction.config.spring.rent;

import ee.qrent.billing.transaction.persistence.adapter.rent.RentCalculationLoadAdapter;
import ee.qrent.billing.transaction.persistence.adapter.rent.RentCalculationPersistenceAdapter;
import ee.qrent.billing.transaction.persistence.mapper.rent.RentCalculationAdapterMapper;
import ee.qrent.billing.transaction.persistence.repository.rent.RentCalculationRepository;
import ee.qrent.billing.transaction.persistence.repository.rent.RentCalculationResultRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RentAdapterConfig {

  @Bean
  RentCalculationPersistenceAdapter getRentCalculationPersistenceAdapter(
      final RentCalculationRepository rentCalculationRepository,
      final RentCalculationResultRepository rentCalculationResultRepository) {

    return new RentCalculationPersistenceAdapter(
        rentCalculationRepository, rentCalculationResultRepository);
  }

  @Bean
  RentCalculationLoadAdapter getRentCalculationLoadAdapter(
      final RentCalculationRepository repository, final RentCalculationAdapterMapper mapper) {
    return new RentCalculationLoadAdapter(repository, mapper);
  }
}
