package ee.qrental.bonus.spring.config;

import ee.qrental.bonus.adapter.mapper.ObligationCalculationAdapterMapper;
import ee.qrental.bonus.core.mapper.ObligationCalculationAddRequestMapper;
import ee.qrental.bonus.core.mapper.ObligationCalculationResponseMapper;
import ee.qrental.bonus.core.mapper.ObligationResponseMapper;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BonusMapperConfig {

  @Bean
  ObligationResponseMapper getObligationResponseMapper() {
    return new ObligationResponseMapper();
  }

  @Bean
  ObligationCalculationAddRequestMapper getObligationCalculationAddRequestMapper() {
    return new ObligationCalculationAddRequestMapper();
  }

  @Bean
  ObligationCalculationResponseMapper getObligationCalculationResponseMapper(
      final GetQWeekQuery qWeekQuery) {

    return new ObligationCalculationResponseMapper(qWeekQuery);
  }

  @Bean
  ObligationCalculationAdapterMapper getObligationCalculationAdapterMapper() {
    return new ObligationCalculationAdapterMapper();
  }
}
