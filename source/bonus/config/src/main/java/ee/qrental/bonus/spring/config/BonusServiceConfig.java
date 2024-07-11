package ee.qrental.bonus.spring.config;

import static java.util.Arrays.asList;

import ee.qrent.common.in.time.QDateTime;
import ee.qrental.bonus.api.in.query.GetBonusCalculationQuery;
import ee.qrental.bonus.api.in.query.GetBonusProgramQuery;
import ee.qrental.bonus.api.in.query.GetObligationQuery;
import ee.qrental.bonus.api.in.usecase.BonusCalculationAddUseCase;
import ee.qrental.bonus.api.out.*;
import ee.qrental.bonus.core.mapper.*;
import ee.qrental.bonus.core.service.*;
import ee.qrental.bonus.core.validator.BonusCalculationAddBusinessRuleValidator;
import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.contract.api.in.query.GetContractQuery;
import ee.qrental.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.email.api.in.usecase.EmailSendUseCase;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrental.user.api.in.query.GetUserAccountQuery;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BonusServiceConfig {

  @Bean
  List<BonusStrategy> getBonusStrategies(
      final GetTransactionQuery transactionQuery,
      final GetTransactionTypeQuery transactionTypeQuery,
      final GetCarLinkQuery carLinkQuery,
      final GetContractQuery contractQuery,
      final GetDriverQuery driverQuery,
      final GetObligationQuery obligationQuery,
      final GetQWeekQuery qWeekQuery,
      final QDateTime qDateTime) {
    return asList(
        new TwoWeeksPrepaymentBonusStrategy(transactionQuery, transactionTypeQuery),
        new FourWeeksPrepaymentBonusStrategy(transactionQuery, transactionTypeQuery),
        new NewDriverBonusStrategy(
            transactionQuery,
            transactionTypeQuery,
            carLinkQuery,
            contractQuery,
            qWeekQuery,
            qDateTime),
        new ReliablePartnerBonusStrategy(transactionQuery, transactionTypeQuery, qWeekQuery),
        new FriendshipBonusStrategy(
            transactionQuery, transactionTypeQuery, driverQuery, obligationQuery, qWeekQuery));
  }

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
      final GetCarLinkQuery carLinkQuery,
      final TransactionAddUseCase transactionAddUseCase,
      final BonusProgramLoadPort bonusProgramLoadPort,
      final GetUserAccountQuery userAccountQuery,
      final EmailSendUseCase emailSendUseCase,
      final BonusCalculationAddPort calculationAddPort,
      final ObligationLoadPort obligationLoadPort,
      final BonusCalculationAddRequestMapper addRequestMapper,
      final BonusCalculationAddBusinessRuleValidator addBusinessRuleValidator,
      final List<BonusStrategy> bonusStrategies) {
    return new BonusCalculationService(
        qWeekQuery,
        transactionQuery,
        carLinkQuery,
        transactionAddUseCase,
        bonusProgramLoadPort,
        userAccountQuery,
        emailSendUseCase,
        calculationAddPort,
        obligationLoadPort,
        addRequestMapper,
        addBusinessRuleValidator,
        bonusStrategies);
  }
}
