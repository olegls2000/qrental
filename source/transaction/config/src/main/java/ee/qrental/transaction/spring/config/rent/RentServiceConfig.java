package ee.qrental.transaction.spring.config.rent;

import ee.qrent.common.in.time.QDateTime;
import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.email.api.in.usecase.EmailSendUseCase;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceCalculationQuery;
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
import ee.qrental.user.api.in.query.GetUserAccountQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RentServiceConfig {

  @Bean
  GetRentCalculationQuery getRentCalculationQueryService(
      final GetQWeekQuery qWeekQuery,
      final GetBalanceCalculationQuery balanceCalculationQuery,
      final RentCalculationLoadPort loadPort,
      final RentCalculationResponseMapper responseMapper) {
    return new RentCalculationQueryService(
        qWeekQuery, balanceCalculationQuery, loadPort, responseMapper);
  }

  @Bean
  RentCalculationService getRentCalculationService(
      final GetCarLinkQuery carLinkQuery,
      final GetCarQuery carQuery,
      final GetTransactionQuery transactionQuery,
      final TransactionUseCaseService transactionUseCaseService,
      final RentCalculationAddPort rentCalculationAddPort,
      final RentCalculationAddRequestMapper addRequestMapper,
      final TransactionTypeLoadPort transactionTypeLoadPort,
      final RentCalculationAddBusinessRuleValidator addBusinessRuleValidator,
      final EmailSendUseCase emailSendUseCase,
      final GetUserAccountQuery userAccountQuery,
      final GetQWeekQuery weekQuery,
      final QDateTime qDateTime) {

    return new RentCalculationService(
        carLinkQuery,
        carQuery,
        transactionQuery,
        transactionUseCaseService,
        rentCalculationAddPort,
        addRequestMapper,
        transactionTypeLoadPort,
        addBusinessRuleValidator,
        emailSendUseCase,
        userAccountQuery,
        weekQuery,
        qDateTime);
  }
}
