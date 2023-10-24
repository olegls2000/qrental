package ee.qrental.transaction.spring.config.rent;

import ee.qrental.transaction.adapter.adapter.rent.RentCalculationLoadAdapter;
import ee.qrental.transaction.adapter.adapter.rent.RentCalculationPersistenceAdapter;
import ee.qrental.transaction.adapter.mapper.rent.RentCalculationAdapterMapper;
import ee.qrental.transaction.adapter.repository.rent.RentCalculationRepository;
import ee.qrental.transaction.adapter.repository.rent.RentCalculationResultRepository;
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
