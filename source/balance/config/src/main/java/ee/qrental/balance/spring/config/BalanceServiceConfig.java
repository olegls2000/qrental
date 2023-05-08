package ee.qrental.balance.spring.config;

import ee.qrental.balance.api.in.query.GetBalanceQuery;
import ee.qrental.balance.api.out.BalanceAddPort;
import ee.qrental.balance.api.out.BalanceCalculationAddPort;
import ee.qrental.balance.api.out.BalanceCalculationLoadPort;
import ee.qrental.balance.api.out.BalanceLoadPort;
import ee.qrental.balance.core.mapper.*;
import ee.qrental.balance.core.service.*;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BalanceServiceConfig {

  @Bean
  GetBalanceQuery getBalanceQueryService(
      final BalanceLoadPort loadPort, final BalanceResponseMapper mapper) {
    return new BalanceQueryService(loadPort, mapper);
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
      final AmountCalculator amountCalculator,
      final FeeCalculator feeCalculator,
      final FeeTransactionCreator feeTransactionCreator) {
    return new BalanceCalculationService(
        balanceCalculationPeriodService,
        addRequestMapper,
        addPort,
        balanceAddPort,
        transactionQuery,
        amountCalculator,
        feeCalculator,
        feeTransactionCreator);
  }

  @Bean
  BalanceCalculationPeriodService getBalanceCalculationPeriodService(
      final BalanceCalculationLoadPort loadPort) {
    return new BalanceCalculationPeriodService(loadPort);
  }

  @Bean
  AmountCalculator getAmountCalculator(final BalanceLoadPort loadPort) {
    return new AmountCalculator(loadPort);
  }

  @Bean
  FeeCalculator getFeeCalculator(
      final BalanceLoadPort loadPort, final GetTransactionQuery transactionQuery) {
    return new FeeCalculator(loadPort, transactionQuery);
  }

  @Bean
  FeeTransactionCreator getFeeTransactionCreator(
      final BalanceLoadPort loadPort,
      final TransactionAddUseCase transactionAddUseCase,
      final GetTransactionTypeQuery transactionTypeQuery) {
    return new FeeTransactionCreator(loadPort, transactionAddUseCase, transactionTypeQuery);
  }
}
