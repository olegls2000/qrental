package ee.qrental.bonus.spring.config;

import ee.qrental.bonus.adapter.adapter.WeekObligationLoadAdapter;
import ee.qrental.bonus.adapter.adapter.WeekObligationPersistenceAdapter;
import ee.qrental.bonus.adapter.mapper.WeekObligationAdapterMapper;
import ee.qrental.bonus.adapter.repository.WeekObligationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BonusAdapterConfig {
  @Bean
  WeekObligationAdapterMapper getFirmAdapterMapper() {
    return new WeekObligationAdapterMapper();
  }

  @Bean
  WeekObligationLoadAdapter getFirmLoadAdapter(
          final WeekObligationRepository repository, final WeekObligationAdapterMapper mapper) {
    return new WeekObligationLoadAdapter(repository, mapper);
  }

  @Bean
  WeekObligationPersistenceAdapter getFirmPersistenceAdapter(
          final WeekObligationRepository repository, final WeekObligationAdapterMapper mapper) {
    return new WeekObligationPersistenceAdapter(repository, mapper);
  }
}
