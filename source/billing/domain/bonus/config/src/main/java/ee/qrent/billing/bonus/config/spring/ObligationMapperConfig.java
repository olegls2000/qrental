package ee.qrent.billing.bonus.config.spring;

import ee.qrent.billing.bonus.persistence.mapper.ObligationCalculationAdapterMapper;
import ee.qrent.billing.bonus.core.mapper.ObligationCalculationAddRequestMapper;
import ee.qrent.billing.bonus.core.mapper.ObligationCalculationResponseMapper;
import ee.qrent.billing.bonus.core.mapper.ObligationResponseMapper;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
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
