package ee.qrental.transaction.spring.config.rent;

import ee.qrental.transaction.adapter.mapper.rent.RentCalculationAdapterMapper;
import ee.qrental.transaction.core.mapper.rent.RentCalculationAddRequestMapper;
import ee.qrental.transaction.core.mapper.rent.RentCalculationResponseMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RentMapperConfig {
  @Bean
  RentCalculationAddRequestMapper getRentCalculationAddRequestMapper() {
    return new RentCalculationAddRequestMapper();
  }

  @Bean
  RentCalculationResponseMapper getRentCalculationResponseMapper() {
    return new RentCalculationResponseMapper();
  }

  @Bean
  RentCalculationAdapterMapper getRentCalculationAdapterMapper() {
    return new RentCalculationAdapterMapper();
  }
}
