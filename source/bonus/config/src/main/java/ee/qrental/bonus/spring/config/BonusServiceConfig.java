package ee.qrental.bonus.spring.config;

import ee.qrental.bonus.api.in.query.GetBonusCalculationQuery;
import ee.qrental.bonus.api.in.query.GetBonusProgramQuery;
import ee.qrental.bonus.api.in.usecase.BonusCalculationAddUseCase;
import ee.qrental.bonus.api.out.*;
import ee.qrental.bonus.core.mapper.*;
import ee.qrental.bonus.core.service.BonusCalculationQueryService;
import ee.qrental.bonus.core.service.BonusCalculationService;
import ee.qrental.bonus.core.service.BonusProgramQueryService;
import ee.qrental.bonus.core.service.BonusProgramService;
import ee.qrental.bonus.core.validator.BonusCalculationAddBusinessRuleValidator;
import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.email.api.in.usecase.EmailSendUseCase;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.user.api.in.query.GetUserAccountQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BonusServiceConfig {

  @Bean
  GetBonusCalculationQuery getGetBonusCalculationQuery(
      final GetQWeekQuery qWeekQuery,
      final BonusCalculationLoadPort loadPort,
      final BonusCalculationResponseMapper responseMapper) {
    return new BonusCalculationQueryService(qWeekQuery, loadPort, responseMapper);
  }

  @Bean
  BonusProgramService gtBonusProgramService(
      final BonusProgramAddPort addPort,
      final BonusProgramUpdatePort updatePort,
      final BonusProgramDeletePort deletePort,
      final BonusProgramAddRequestMapper addRequestMapper,
      final BonusProgramUpdateRequestMapper updateRequestMapper) {
    return new BonusProgramService(
        addPort, updatePort, deletePort, addRequestMapper, updateRequestMapper);
  }

  @Bean
  GetBonusProgramQuery getBonusProgramQuery(
      final BonusProgramLoadPort loadPort,
      final BonusProgramResponseMapper responseMapper,
      final BonusProgramUpdateRequestMapper updateRequestMapper) {
    return new BonusProgramQueryService(loadPort, responseMapper, updateRequestMapper);
  }

  @Bean
  public BonusCalculationAddUseCase getBonusCalculationAddUseCase(
      final GetQWeekQuery qWeekQuery,
      final GetTransactionQuery transactionQuery,
      final GetTransactionTypeQuery transactionTypeQuery,
      final BonusProgramLoadPort bonusProgramLoadPort,
      final GetCarLinkQuery carLinkQuery,
      final GetUserAccountQuery userAccountQuery,
      final EmailSendUseCase emailSendUseCase,
      final BonusCalculationAddPort calculationAddPort,
      final ObligationLoadPort obligationLoadPort,
      final BonusCalculationAddRequestMapper addRequestMapper,
      final BonusCalculationAddBusinessRuleValidator addBusinessRuleValidator) {
    return new BonusCalculationService(
        qWeekQuery,
        transactionQuery,
        transactionTypeQuery,
        bonusProgramLoadPort,
        carLinkQuery,
        userAccountQuery,
        emailSendUseCase,
        calculationAddPort,
        obligationLoadPort,
        addRequestMapper,
        addBusinessRuleValidator);
  }
}
