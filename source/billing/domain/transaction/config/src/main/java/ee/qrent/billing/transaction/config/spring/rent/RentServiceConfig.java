package ee.qrent.billing.transaction.config.spring.rent;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.car.api.in.query.GetCarQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.contract.api.in.query.GetAbsenceQuery;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceCalculationQuery;
import ee.qrent.billing.transaction.api.in.query.rent.GetRentCalculationQuery;
import ee.qrent.billing.transaction.api.in.request.rent.RentCalculationAddRequest;
import ee.qrent.billing.transaction.api.out.rent.RentCalculationAddPort;
import ee.qrent.billing.transaction.api.out.rent.RentCalculationLoadPort;
import ee.qrent.billing.transaction.api.out.type.TransactionTypeLoadPort;
import ee.qrent.billing.transaction.core.mapper.rent.RentCalculationAddRequestMapper;
import ee.qrent.billing.transaction.core.mapper.rent.RentCalculationResponseMapper;
import ee.qrent.billing.transaction.core.service.TransactionUseCaseService;
import ee.qrent.billing.transaction.core.service.rent.RentCalculationQueryService;
import ee.qrent.billing.transaction.core.service.rent.RentCalculationService;
import ee.qrent.billing.transaction.core.service.rent.RentTransactionGenerator;
import ee.qrent.billing.user.api.in.query.GetUserAccountQuery;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
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
      final RentTransactionGenerator rentTransactionGenerator,
      final GetCarLinkQuery carLinkQuery,
      final GetTransactionQuery transactionQuery,
      final TransactionUseCaseService transactionUseCaseService,
      final RentCalculationAddPort rentCalculationAddPort,
      final RentCalculationAddRequestMapper addRequestMapper,
      final AddRequestValidator<RentCalculationAddRequest> addRequestValidator,
      final GetUserAccountQuery userAccountQuery,
      final GetQWeekQuery weekQuery,
      final GetAbsenceQuery absenceQuery,
      final QueueEntryPushUseCase notificationQueuePushUseCase,
      final QDateTime qDateTime) {

    return new RentCalculationService(
        rentTransactionGenerator,
        carLinkQuery,
        transactionQuery,
        transactionUseCaseService,
        rentCalculationAddPort,
        addRequestMapper,
        addRequestValidator,
        userAccountQuery,
        weekQuery,
        absenceQuery,
        notificationQueuePushUseCase,
        qDateTime);
  }

  @Bean
  RentTransactionGenerator getRentTransactionGenerator(
      final TransactionTypeLoadPort transactionTypeLoadPort,
      final GetCarQuery carQuery,
      final QDateTime qDateTime) {

    return new RentTransactionGenerator(transactionTypeLoadPort, carQuery, qDateTime);
  }
}
