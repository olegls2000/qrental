package ee.qrent.billing.bonus.config.spring;

import static java.util.Arrays.asList;

import ee.qrent.billing.bonus.api.out.*;
import ee.qrent.billing.bonus.core.mapper.*;
import ee.qrent.billing.bonus.core.service.*;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.bonus.api.in.query.GetBonusCalculationQuery;
import ee.qrent.billing.bonus.api.in.query.GetBonusProgramQuery;
import ee.qrent.billing.bonus.api.in.query.GetObligationQuery;
import ee.qrent.billing.bonus.api.in.request.BonusCalculationAddRequest;
import ee.qrent.billing.bonus.api.in.usecase.BonusCalculationAddUseCase;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.billing.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrent.billing.user.api.in.query.GetUserAccountQuery;
import java.util.List;

import ee.qrent.queue.api.in.QueueEntryPushUseCase;
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
      final QueueEntryPushUseCase notificationQueuePushUseCase,
      final BonusCalculationAddPort calculationAddPort,
      final ObligationLoadPort obligationLoadPort,
      final BonusCalculationAddRequestMapper addRequestMapper,
      final AddRequestValidator<BonusCalculationAddRequest> addRequestValidator,
      final List<BonusStrategy> bonusStrategies,
      final QDateTime qDateTime) {
    return new BonusCalculationService(
        qWeekQuery,
        transactionQuery,
        carLinkQuery,
        transactionAddUseCase,
        bonusProgramLoadPort,
        userAccountQuery,
        notificationQueuePushUseCase,
        calculationAddPort,
        obligationLoadPort,
        addRequestMapper,
        addRequestValidator,
        bonusStrategies,
        qDateTime);
  }
}
