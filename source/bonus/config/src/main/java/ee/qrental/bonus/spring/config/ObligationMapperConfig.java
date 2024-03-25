package ee.qrental.bonus.spring.config;

import ee.qrental.bonus.adapter.mapper.ObligationCalculationAdapterMapper;
import ee.qrental.bonus.core.mapper.BonusProgramResponseMapper;
import ee.qrental.bonus.core.mapper.ObligationCalculationAddRequestMapper;
import ee.qrental.bonus.core.mapper.ObligationCalculationResponseMapper;
import ee.qrental.bonus.core.mapper.ObligationResponseMapper;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObligationMapperConfig {

  @Bean
  ObligationResponseMapper getObligationResponseMapper(final GetDriverQuery driverQuery) {
    return new ObligationResponseMapper(driverQuery);
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
