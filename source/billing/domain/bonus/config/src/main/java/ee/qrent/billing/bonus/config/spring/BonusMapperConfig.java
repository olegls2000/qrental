package ee.qrent.billing.bonus.config.spring;

import ee.qrent.billing.bonus.core.mapper.*;
import ee.qrent.billing.bonus.persistence.mapper.BonusCalculationAdapterMapper;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BonusMapperConfig {

  @Bean
  BonusProgramResponseMapper getBonusProgramResponseMapper() {
    return new BonusProgramResponseMapper();
  }

  @Bean
  BonusProgramAddRequestMapper gtBonusProgramAddRequestMapper() {
    return new BonusProgramAddRequestMapper();
  }

  @Bean
  BonusProgramUpdateRequestMapper getBonusProgramUpdateRequestMapper() {
    return new BonusProgramUpdateRequestMapper();
  }

  @Bean
  BonusCalculationAddRequestMapper getBonusCalculationAddRequestMapper() {
    return new BonusCalculationAddRequestMapper();
  }

  @Bean
  BonusCalculationResponseMapper getBonusCalculationResponseMapper(final GetQWeekQuery qWeekQuery) {

    return new BonusCalculationResponseMapper(qWeekQuery);
  }

  @Bean
  BonusCalculationAdapterMapper getBonusCalculationAdapterMapper() {
    return new BonusCalculationAdapterMapper();
  }
}
