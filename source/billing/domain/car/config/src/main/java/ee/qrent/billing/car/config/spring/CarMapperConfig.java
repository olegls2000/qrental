package ee.qrent.billing.car.config.spring;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.car.core.mapper.CarAddRequestMapper;
import ee.qrent.billing.car.core.mapper.CarResponseMapper;
import ee.qrent.billing.car.core.mapper.CarUpdateRequestMapper;
import ee.qrent.billing.car.core.service.CarWarrantyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarMapperConfig {
  @Bean
  CarAddRequestMapper getCarAddRequestMapper() {
    return new CarAddRequestMapper();
  }

  @Bean
  CarResponseMapper getCarResponseMapper(
      final QDateTime qDateTime, final CarWarrantyService warrantyService) {
    return new CarResponseMapper(qDateTime, warrantyService);
  }

  @Bean
  CarUpdateRequestMapper getCarUpdateRequestMapper() {
    return new CarUpdateRequestMapper();
  }
}
