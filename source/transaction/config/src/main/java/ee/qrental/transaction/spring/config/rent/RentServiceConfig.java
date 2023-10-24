package ee.qrental.transaction.spring.config.rent;

import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.transaction.api.in.query.rent.GetRentCalculationQuery;
import ee.qrental.transaction.api.out.rent.RentCalculationAddPort;
import ee.qrental.transaction.api.out.rent.RentCalculationLoadPort;
import ee.qrental.transaction.api.out.type.TransactionTypeLoadPort;
import ee.qrental.transaction.core.mapper.rent.RentCalculationAddRequestMapper;
import ee.qrental.transaction.core.mapper.rent.RentCalculationResponseMapper;
import ee.qrental.transaction.core.service.TransactionUseCaseService;
import ee.qrental.transaction.core.service.rent.RentCalculationQueryService;
import ee.qrental.transaction.core.service.rent.RentCalculationService;
import ee.qrental.transaction.core.validator.RentCalculationAddBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RentServiceConfig {

  @Bean
  GetRentCalculationQuery getRentCalculationQueryService(
      final RentCalculationLoadPort loadPort, final RentCalculationResponseMapper responseMapper) {
    return new RentCalculationQueryService(loadPort, responseMapper);
  }

  @Bean
  RentCalculationService getRentCalculationService(
      final GetCarLinkQuery carLinkQuery,
      final GetCarQuery carQuery,
      final TransactionUseCaseService transactionUseCaseService,
      final RentCalculationAddPort rentCalculationAddPort,
      final RentCalculationAddRequestMapper addRequestMapper,
      final TransactionTypeLoadPort transactionTypeLoadPort,
      final RentCalculationAddBusinessRuleValidator addBusinessRuleValidator) {

    return new RentCalculationService(
        carLinkQuery,
        carQuery,
        transactionUseCaseService,
        rentCalculationAddPort,
        addRequestMapper,
        transactionTypeLoadPort,
        addBusinessRuleValidator);
  }
}
