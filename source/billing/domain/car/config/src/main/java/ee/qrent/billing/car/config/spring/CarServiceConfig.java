package ee.qrent.billing.car.config.spring;

import ee.qrent.billing.car.api.out.*;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.car.api.in.query.GetCarQuery;
import ee.qrent.billing.car.core.mapper.CarAddRequestMapper;
import ee.qrent.billing.car.core.mapper.CarResponseMapper;
import ee.qrent.billing.car.core.mapper.CarUpdateRequestMapper;
import ee.qrent.billing.car.core.service.CarQueryService;
import ee.qrent.billing.car.core.service.CarUseCaseService;
import ee.qrent.billing.car.core.service.CarWarrantyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarServiceConfig {

  @Bean
  public GetCarQuery getCarQueryService(
      final CarLoadPort carLoadPort,
      final CarResponseMapper carMapper,
      final CarUpdateRequestMapper carUpdateRequestMapper,
      final CarLinkLoadPort carLinkLoadPort) {

    return new CarQueryService(carLoadPort, carMapper, carUpdateRequestMapper, carLinkLoadPort);
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

  @Bean
  CarWarrantyService getCarWarrantyService(final QDateTime qDateTime) {

    return new CarWarrantyService(qDateTime);
  }
}
