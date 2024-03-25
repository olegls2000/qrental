package ee.qrental.bonus.spring.config;

import ee.qrental.bonus.api.in.query.GetObligationCalculationQuery;
import ee.qrental.bonus.api.in.query.GetObligationQuery;
import ee.qrental.bonus.api.in.usecase.ObligationCalculationAddUseCase;
import ee.qrental.bonus.api.out.ObligationAddPort;
import ee.qrental.bonus.api.out.ObligationCalculationAddPort;
import ee.qrental.bonus.api.out.ObligationCalculationLoadPort;
import ee.qrental.bonus.api.out.ObligationLoadPort;
import ee.qrental.bonus.core.mapper.ObligationCalculationAddRequestMapper;
import ee.qrental.bonus.core.mapper.ObligationCalculationResponseMapper;
import ee.qrental.bonus.core.mapper.ObligationResponseMapper;
import ee.qrental.bonus.core.service.ObligationCalculationQueryService;
import ee.qrental.bonus.core.service.ObligationCalculationService;
import ee.qrental.bonus.core.service.ObligationCalculator;
import ee.qrental.bonus.core.service.ObligationQueryService;
import ee.qrental.bonus.core.validator.ObligationCalculationAddBusinessRuleValidator;
import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.email.api.in.usecase.EmailSendUseCase;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.user.api.in.query.GetUserAccountQuery;
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
      final EmailSendUseCase emailSendUseCase,
      final ObligationCalculationAddPort calculationAddPort,
      final ObligationAddPort obligationAddPort,
      final ObligationLoadPort loadPort,
      final ObligationCalculationAddRequestMapper addRequestMapper,
      final ObligationCalculationAddBusinessRuleValidator addBusinessRuleValidator,
      final ObligationCalculator obligationCalculator) {
    return new ObligationCalculationService(
        qWeekQuery,
        transactionQuery,
        carLinkQuery,
        userAccountQuery,
        emailSendUseCase,
        calculationAddPort,
        obligationAddPort,
        loadPort,
        addRequestMapper,
        addBusinessRuleValidator,
        obligationCalculator);
  }
}
