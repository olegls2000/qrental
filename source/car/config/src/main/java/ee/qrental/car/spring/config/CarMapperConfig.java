package ee.qrental.car.spring.config;

import ee.qrental.car.core.mapper.CarAddRequestMapper;
import ee.qrental.car.core.mapper.CarResponseMapper;
import ee.qrental.car.core.mapper.CarUpdateRequestMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarMapperConfig {
  @Bean
  CarAddRequestMapper getCarAddRequestMapper() {
    return new CarAddRequestMapper();
  }

  @Bean
  CarResponseMapper getCarResponseMapper() {
    return new CarResponseMapper();
  }

  @Bean
  CarUpdateRequestMapper getCarUpdateRequestMapper() {
    return new CarUpdateRequestMapper();
  }
}
