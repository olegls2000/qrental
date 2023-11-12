package ee.qrental.transaction.spring.config.balance;

import ee.qrental.constant.api.in.query.GetConstantQuery;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.api.out.balance.BalanceAddPort;
import ee.qrental.transaction.api.out.balance.BalanceCalculationAddPort;
import ee.qrental.transaction.api.out.balance.BalanceCalculationLoadPort;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.core.mapper.balance.BalanceCalculationAddRequestMapper;
import ee.qrental.transaction.core.mapper.balance.BalanceCalculationResponseMapper;
import ee.qrental.transaction.core.mapper.balance.BalanceResponseMapper;
import ee.qrental.transaction.core.service.balance.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BalanceServiceConfig {

  @Bean
  GetBalanceQuery getBalanceQueryService(
      final GetQWeekQuery qWeekQuery,
      final GetConstantQuery constantQuery,
      final BalanceLoadPort balanceLoadPort,
      final TransactionLoadPort transactionLoadPort,
      final BalanceResponseMapper balanceResponseMapper) {
    return new BalanceQueryService(
        qWeekQuery, constantQuery, balanceLoadPort, transactionLoadPort, balanceResponseMapper);
  }

  @Bean
  BalanceCalculationQueryService getBalanceCalculationQueryService(
      final GetQWeekQuery qWeekQuery,
      final BalanceCalculationLoadPort loadPort,
      final BalanceCalculationResponseMapper responseMapper) {
    return new BalanceCalculationQueryService(qWeekQuery, loadPort, responseMapper);
  }

  @Bean
  BalanceCalculationService getBalanceCalculationService(
      final GetQWeekQuery qWeekQuery,
      final GetDriverQuery driverQuery,
      final GetTransactionQuery transactionQuery,
      final FeeCalculationService feeCalculationService,
      final FeeReplenishService feeReplenishService,
      final BalanceCalculationAddPort balanceCalculationAddPort,
      final BalanceAddPort balanceAddPort,
      final BalanceLoadPort balanceLoadPort,
      final BalanceCalculationLoadPort balanceCalculationLoadPort,
      final BalanceCalculationAddRequestMapper addRequestMapper) {
    return new BalanceCalculationService(
        qWeekQuery,
        driverQuery,
        transactionQuery,
        feeCalculationService,
        feeReplenishService,
        balanceCalculationAddPort,
        balanceAddPort,
        balanceLoadPort,
        balanceCalculationLoadPort,
        addRequestMapper);
  }

  @Bean
  FeeCalculationService getFeeTransactionCreator(
      final GetQWeekQuery qWeekQuery,
      final GetTransactionQuery transactionQuery,
      final GetTransactionTypeQuery transactionTypeQuery,
      final GetConstantQuery constantQuery,
      final TransactionAddUseCase transactionAddUseCase,
      final BalanceLoadPort loadPort) {
    return new FeeCalculationService(
        qWeekQuery,
        transactionQuery,
        transactionTypeQuery,
        constantQuery,
        transactionAddUseCase,
        loadPort);
  }

  @Bean
  FeeReplenishService getFeeDebtReplenishService(
      final GetQWeekQuery qWeekQuery,
      final GetTransactionQuery transactionQuery,
      final GetTransactionTypeQuery transactionTypeQuery,
      final TransactionAddUseCase transactionAddUseCase,
      final BalanceLoadPort balanceLoadPort) {
    return new FeeReplenishService(
        qWeekQuery, transactionQuery, transactionTypeQuery, transactionAddUseCase, balanceLoadPort);
  }
}
