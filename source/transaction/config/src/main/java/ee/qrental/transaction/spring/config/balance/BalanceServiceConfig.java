package ee.qrental.transaction.spring.config.balance;

import ee.qrental.constant.api.in.query.GetConstantQuery;
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
           final GetDriverQuery driverQuery,
   final BalanceLoadPort balanceLoadPort,
   final TransactionLoadPort transactionLoadPort,
   final BalanceResponseMapper balanceResponseMapper) {
    return new BalanceQueryService(driverQuery, balanceLoadPort, transactionLoadPort, balanceResponseMapper);
  }

  @Bean
  BalanceCalculationQueryService getBalanceCalculationQueryService(
      final BalanceCalculationLoadPort loadPort,
      final BalanceCalculationResponseMapper responseMapper) {
    return new BalanceCalculationQueryService(loadPort, responseMapper);
  }

  @Bean
  BalanceCalculationService getBalanceCalculationService(
      final BalanceCalculationPeriodService balanceCalculationPeriodService,
      final BalanceCalculationAddRequestMapper addRequestMapper,
      final BalanceCalculationAddPort addPort,
      final BalanceAddPort balanceAddPort,
      final GetTransactionQuery transactionQuery,
      final FeeCalculationService feeTransactionCreator,
      final FeeReplenishService replenishService,
      final GetDriverQuery driverQuery,
      final BalanceLoadPort loadPort) {
    return new BalanceCalculationService(
        balanceCalculationPeriodService,
        addRequestMapper,
        addPort,
        balanceAddPort,
        transactionQuery,
        feeTransactionCreator,
        replenishService,
        driverQuery,
        loadPort);
  }

  @Bean
  BalanceCalculationPeriodService getBalanceCalculationPeriodService(
      final BalanceCalculationLoadPort loadPort) {
    return new BalanceCalculationPeriodService(loadPort);
  }

  @Bean
  FeeCalculationService getFeeTransactionCreator(
      final BalanceLoadPort loadPort,
      final TransactionAddUseCase transactionAddUseCase,
      final GetTransactionTypeQuery transactionTypeQuery,
      final GetConstantQuery constantQuery) {
    return new FeeCalculationService(loadPort, transactionAddUseCase, transactionTypeQuery, constantQuery);
  }
  @Bean
  FeeReplenishService getFeeDebtReplenishService(
          final BalanceLoadPort loadPort,
          final TransactionAddUseCase transactionAddUseCase,
          final GetTransactionTypeQuery transactionTypeQuery,
          final GetTransactionQuery transactionQuery) {
    return new FeeReplenishService(
            loadPort,
            transactionAddUseCase,
            transactionTypeQuery,
            transactionQuery);
  }
}
