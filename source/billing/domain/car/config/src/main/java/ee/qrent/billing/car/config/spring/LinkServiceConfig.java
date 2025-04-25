package ee.qrent.billing.car.config.spring;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.car.api.in.request.CarLinkAddRequest;
import ee.qrent.billing.car.api.in.request.CarLinkUpdateRequest;
import ee.qrent.billing.car.api.out.CarLinkAddPort;
import ee.qrent.billing.car.api.out.CarLinkDeletePort;
import ee.qrent.billing.car.api.out.CarLinkLoadPort;
import ee.qrent.billing.car.api.out.CarLinkUpdatePort;
import ee.qrent.billing.car.core.mapper.CarLinkAddRequestMapper;
import ee.qrent.billing.car.core.mapper.CarLinkResponseMapper;
import ee.qrent.billing.car.core.mapper.CarLinkUpdateRequestMapper;
import ee.qrent.billing.car.core.service.CarLinkQueryService;
import ee.qrent.billing.car.core.service.CarLinkUseCaseService;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkServiceConfig {

  @Bean
  public GetCarLinkQuery getLinkQueryService(
      final CarLinkLoadPort loadPort,
      final CarLinkResponseMapper mapper,
      final CarLinkUpdateRequestMapper updateRequestMapper,
      final QDateTime qDateTime,
      final GetQWeekQuery qWeekQuery) {

    return new CarLinkQueryService(loadPort, mapper, updateRequestMapper, qDateTime, qWeekQuery);
  }

  @Bean
  public CarLinkUseCaseService getLinkUseCaseService(
      final CarLinkAddPort addPort,
      final CarLinkUpdatePort updatePort,
      final CarLinkDeletePort deletePort,
      final CarLinkLoadPort loadPort,
      final CarLinkAddRequestMapper addRequestMapper,
      final AddRequestValidator<CarLinkAddRequest> addRequestValidator,
      final UpdateRequestValidator<CarLinkUpdateRequest> updateRequestValidator,
      final QDateTime qDateTime) {

    return new CarLinkUseCaseService(
        addPort,
        updatePort,
        deletePort,
        loadPort,
        addRequestMapper,
        addRequestValidator,
        updateRequestValidator,
        qDateTime);
  }
}
