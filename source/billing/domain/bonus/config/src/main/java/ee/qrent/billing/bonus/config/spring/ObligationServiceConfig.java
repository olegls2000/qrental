package ee.qrent.billing.bonus.config.spring;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.bonus.api.in.query.GetObligationCalculationQuery;
import ee.qrent.billing.bonus.api.in.query.GetObligationQuery;
import ee.qrent.billing.bonus.api.in.request.ObligationCalculationAddRequest;
import ee.qrent.billing.bonus.api.in.usecase.ObligationCalculationAddUseCase;
import ee.qrent.billing.bonus.api.out.ObligationAddPort;
import ee.qrent.billing.bonus.api.out.ObligationCalculationAddPort;
import ee.qrent.billing.bonus.api.out.ObligationCalculationLoadPort;
import ee.qrent.billing.bonus.api.out.ObligationLoadPort;
import ee.qrent.billing.bonus.core.mapper.ObligationCalculationAddRequestMapper;
import ee.qrent.billing.bonus.core.mapper.ObligationCalculationResponseMapper;
import ee.qrent.billing.bonus.core.mapper.ObligationResponseMapper;
import ee.qrent.billing.bonus.core.service.ObligationCalculationQueryService;
import ee.qrent.billing.bonus.core.service.ObligationCalculationService;
import ee.qrent.billing.bonus.core.service.ObligationCalculator;
import ee.qrent.billing.bonus.core.service.ObligationQueryService;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrent.billing.user.api.in.query.GetUserAccountQuery;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObligationServiceConfig {

  @Bean
  GetObligationCalculationQuery getGetObligationCalculationQuery(
      final GetQWeekQuery qWeekQuery,
      final ObligationCalculationLoadPort loadPort,
      final ObligationCalculationResponseMapper responseMapper) {

    return new ObligationCalculationQueryService(qWeekQuery, loadPort, responseMapper);
  }

  @Bean
  GetObligationQuery getGetObligationQuery(
      final GetQWeekQuery qWeekQuery,
      final ObligationLoadPort loadPort,
      final ObligationCalculator obligationCalculator,
      final ObligationResponseMapper responseMapper) {

    return new ObligationQueryService(qWeekQuery, loadPort, obligationCalculator, responseMapper);
  }

  @Bean
  ObligationCalculator getObligationCalculator(
      final GetQWeekQuery qWeekQuery,
      final GetBalanceQuery balanceQuery,
      final GetDriverQuery driverQuery,
      final GetTransactionQuery transactionQuery) {

    return new ObligationCalculator(qWeekQuery, balanceQuery, driverQuery, transactionQuery);
  }

  @Bean
  public ObligationCalculationAddUseCase getObligationCalculationAddUseCase(
      final GetQWeekQuery qWeekQuery,
      final GetTransactionQuery transactionQuery,
      final GetCarLinkQuery carLinkQuery,
      final GetUserAccountQuery userAccountQuery,
      final QueueEntryPushUseCase queueEntryPushUseCase,
      final ObligationCalculationAddPort calculationAddPort,
      final ObligationAddPort obligationAddPort,
      final ObligationLoadPort loadPort,
      final ObligationCalculationAddRequestMapper addRequestMapper,
      final AddRequestValidator<ObligationCalculationAddRequest> addRequestValidator,
      final ObligationCalculator obligationCalculator,
      final QDateTime qDateTime) {

    return new ObligationCalculationService(
        qWeekQuery,
        transactionQuery,
        carLinkQuery,
        userAccountQuery,
        queueEntryPushUseCase,
        calculationAddPort,
        obligationAddPort,
        loadPort,
        addRequestMapper,
        addRequestValidator,
        obligationCalculator,
        qDateTime);
  }
}
