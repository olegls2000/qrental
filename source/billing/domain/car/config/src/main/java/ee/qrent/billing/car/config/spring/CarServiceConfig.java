package ee.qrent.billing.car.config.spring;

import ee.qrent.billing.car.api.in.query.GetBrandingVerificationCalculationQuery;
import ee.qrent.billing.car.api.in.query.GetCarQuery;
import ee.qrent.billing.car.api.in.usecase.BrandingVerificationCalculationAddUseCase;
import ee.qrent.billing.car.api.out.*;
import ee.qrent.billing.car.core.mapper.BrandingVerificationCalculationResultResponseMapper;
import ee.qrent.billing.car.core.mapper.BrandingVerificationCalculationSummaryResponseMapper;
import ee.qrent.billing.car.core.mapper.CarAddRequestMapper;
import ee.qrent.billing.car.core.mapper.CarResponseMapper;
import ee.qrent.billing.car.core.mapper.CarUpdateRequestMapper;
import ee.qrent.billing.car.core.service.BrandingVerificationCalculationQueryService;
import ee.qrent.billing.car.core.service.BrandingVerificationCalculationUseCaseService;
import ee.qrent.billing.car.core.service.CarQueryService;
import ee.qrent.billing.car.core.service.CarUseCaseService;
import ee.qrent.billing.car.core.service.CarWarrantyService;
import ee.qrent.billing.car.core.validator.CarAddRequestValidator;
import ee.qrent.billing.car.core.validator.CarUpdateRequestValidator;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
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
      final CarAddRequestMapper addRequestMapper,
      final CarUpdateRequestMapper updateRequestMapper,
      final CarAddRequestValidator addRequestValidator,
      final CarUpdateRequestValidator updateRequestValidator) {

    return new CarUseCaseService(
        addPort,
        updatePort,
        deletePort,
        addRequestMapper,
        updateRequestMapper,
        addRequestValidator,
        updateRequestValidator);
  }

  @Bean
  CarWarrantyService getCarWarrantyService(final QDateTime qDateTime) {

    return new CarWarrantyService(qDateTime);
  }

  @Bean
  public BrandingVerificationCalculationAddUseCase getBrandingVerificationCalculationAddUseCase(
      final CarLoadPort carLoadPort,
      final CarLinkLoadPort carLinkLoadPort,
      final BrandingVerificationCalculationAddPort calculationAddPort,
      final GetDriverQuery driverQuery,
      final QueueEntryPushUseCase notificationQueuePushUseCase,
      final QDateTime qDateTime) {
    return new BrandingVerificationCalculationUseCaseService(
        carLoadPort,
        carLinkLoadPort,
        calculationAddPort,
        driverQuery,
        notificationQueuePushUseCase,
        qDateTime);
  }

  @Bean
  public GetBrandingVerificationCalculationQuery getBrandingVerificationCalculationQueryService(
      final BrandingVerificationCalculationResultLoadPort loadPort,
      final BrandingVerificationCalculationResultResponseMapper mapper,
      final BrandingVerificationCalculationSummaryResponseMapper summaryMapper) {
    return new BrandingVerificationCalculationQueryService(loadPort, mapper, summaryMapper);
  }
}
