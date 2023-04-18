package ee.qrental.car.spring.config;

import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.car.api.out.CarAddPort;
import ee.qrental.car.api.out.CarDeletePort;
import ee.qrental.car.api.out.CarLoadPort;
import ee.qrental.car.api.out.CarUpdatePort;
import ee.qrental.car.core.mapper.CarAddRequestMapper;
import ee.qrental.car.core.mapper.CarResponseMapper;
import ee.qrental.car.core.mapper.CarUpdateRequestMapper;
import ee.qrental.car.core.service.CarQueryService;
import ee.qrental.car.core.service.CarUseCaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarServiceConfig {

  @Bean
  public GetCarQuery getCarQueryService(
      final CarLoadPort loadPort,
      final CarResponseMapper mapper,
      final CarUpdateRequestMapper updateRequestMapper) {
    return new CarQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  public CarUseCaseService getCarUseCaseService(
      final CarAddPort addPort,
      final CarUpdatePort updatePort,
      final CarDeletePort deletePort,
      final CarLoadPort loadPort,
      final CarAddRequestMapper addRequestMapper,
      final CarUpdateRequestMapper updateRequestMapper) {
    return new CarUseCaseService(
        addPort, updatePort, deletePort, loadPort, addRequestMapper, updateRequestMapper);
  }
}
