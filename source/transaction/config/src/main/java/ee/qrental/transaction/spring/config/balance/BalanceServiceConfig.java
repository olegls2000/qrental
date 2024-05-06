package ee.qrental.transaction.spring.config.balance;

import ee.qrental.constant.api.in.query.GetConstantQuery;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.transaction.api.in.query.kind.GetTransactionKindQuery;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.api.out.balance.BalanceAddPort;
import ee.qrental.transaction.api.out.balance.BalanceCalculationAddPort;
import ee.qrental.transaction.api.out.balance.BalanceCalculationLoadPort;
import ee.qrental.transaction.api.out.balance.BalanceLoadPort;
import ee.qrental.transaction.api.out.kind.TransactionKindLoadPort;
import ee.qrental.transaction.api.out.type.TransactionTypeLoadPort;
import ee.qrental.transaction.core.mapper.balance.BalanceCalculationAddRequestMapper;
import ee.qrental.transaction.core.mapper.balance.BalanceCalculationResponseMapper;
import ee.qrental.transaction.core.mapper.balance.BalanceResponseMapper;
import ee.qrental.transaction.core.service.balance.*;
import ee.qrental.transaction.core.service.balance.calculator.BalanceCalculationStrategyDryRun;
import ee.qrental.transaction.core.service.balance.calculator.BalanceCalculatorStrategy;
import ee.qrental.transaction.core.service.balance.calculator.BalanceCalculatorStrategySaving;
import ee.qrental.transaction.core.service.balance.calculator.BalanceDeriveService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

import static java.util.Arrays.asList;

@Configuration
@EnableTransactionManagement
public class BalanceServiceConfig {

  @Bean
  public List<BalanceCalculatorStrategy> getBalanceCalculatorStrategies(
      final BalanceDeriveService balanceDeriveService,
      final GetConstantQuery constantQuery,
      final GetTransactionQuery transactionQuery,
      final TransactionAddUseCase transactionAddUseCase,
      final TransactionTypeLoadPort transactionTypeLoadPort,
      final BalanceAddPort balanceAddPort) {
    return asList(
        new BalanceCalculationStrategyDryRun(balanceDeriveService, constantQuery),
        new BalanceCalculatorStrategySaving(
            transactionAddUseCase,
            balanceDeriveService,
            constantQuery,
            transactionQuery,
            transactionTypeLoadPort,
            balanceAddPort));
  }

  @Bean
  GetBalanceQuery getBalanceQueryService(
      final GetDriverQuery driverQuery,
      final GetQWeekQuery qWeekQuery,
      final GetTransactionQuery transactionQuery,
      final GetTransactionKindQuery transactionKindQuery,
      final BalanceLoadPort balanceLoadPort,
      final TransactionLoadPort transactionLoadPort,
      final BalanceResponseMapper balanceResponseMapper,
      final List<BalanceCalculatorStrategy> calculatorStrategies) {
    return new BalanceQueryService(
        driverQuery,
        qWeekQuery,
        transactionQuery,
        transactionKindQuery,
        balanceLoadPort,
        transactionLoadPort,
        balanceResponseMapper,
        calculatorStrategies);
  }

  @Bean
  BalanceCalculationQueryService getBalanceCalculationQueryService(
      final GetQWeekQuery qWeekQuery,
      final BalanceCalculationLoadPort loadPort,
      final BalanceCalculationResponseMapper responseMapper) {
    return new BalanceCalculationQueryService(qWeekQuery, loadPort, responseMapper);
  }

  @Bean
  BalanceDeriveService getBalanceDeriveService() {
    return new BalanceDeriveService();
  }

  @Bean
  BalanceCalculationService getBalanceCalculationService(
      final GetTransactionQuery transactionQuery,
      final GetQWeekQuery qWeekQuery,
      final GetDriverQuery driverQuery,
      final BalanceCalculationLoadPort balanceCalculationLoadPort,
      final TransactionLoadPort transactionLoadPort,
      final BalanceCalculationAddPort balanceCalculationAddPort,
      final BalanceLoadPort balanceLoadPort,
      final BalanceCalculationAddRequestMapper addRequestMapper,
      final List<BalanceCalculatorStrategy> calculatorStrategies) {
    return new BalanceCalculationService(
        transactionQuery,
        qWeekQuery,
        driverQuery,
        balanceCalculationLoadPort,
        transactionLoadPort,
        balanceCalculationAddPort,
        balanceLoadPort,
        addRequestMapper,
        calculatorStrategies);
  }
}
