package ee.qrental.bonus.spring.config;

import ee.qrental.bonus.adapter.repository.WeekObligationRepository;
import ee.qrental.bonus.repository.impl.WeekObligationRepositoryImpl;
import ee.qrental.bonus.repository.spring.WeekObligationSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BonusRepositoryConfig {

  @Bean
  WeekObligationRepository getFirmRepository(final WeekObligationSpringDataRepository springDataRepository) {
    return new WeekObligationRepositoryImpl(springDataRepository);
  }
}
