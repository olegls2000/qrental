package ee.qrent.billing.transaction.config.spring.balance;

import ee.qrent.billing.constant.api.in.query.GetConstantQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrent.billing.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrent.billing.transaction.api.out.TransactionLoadPort;
import ee.qrent.billing.transaction.api.out.balance.BalanceAddPort;
import ee.qrent.billing.transaction.api.out.balance.BalanceCalculationAddPort;
import ee.qrent.billing.transaction.api.out.balance.BalanceCalculationLoadPort;
import ee.qrent.billing.transaction.api.out.balance.BalanceLoadPort;
import ee.qrent.billing.transaction.api.out.type.TransactionTypeLoadPort;
import ee.qrent.billing.transaction.core.mapper.balance.BalanceCalculationAddRequestMapper;
import ee.qrent.billing.transaction.core.mapper.balance.BalanceCalculationResponseMapper;
import ee.qrent.billing.transaction.core.mapper.balance.BalanceResponseMapper;
import ee.qrent.billing.transaction.core.service.balance.BalanceCalculationQueryService;
import ee.qrent.billing.transaction.core.service.balance.BalanceCalculationService;
import ee.qrent.billing.transaction.core.service.balance.BalanceQueryService;
import ee.qrent.billing.transaction.core.service.balance.calculator.BalanceCalculationStrategyDryRun;
import ee.qrent.billing.transaction.core.service.balance.calculator.BalanceCalculatorStrategy;
import ee.qrent.billing.transaction.core.service.balance.calculator.BalanceCalculatorStrategySaving;
import ee.qrent.billing.transaction.core.service.balance.calculator.BalanceDeriveService;
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
      final BalanceLoadPort balanceLoadPort,
      final BalanceResponseMapper balanceResponseMapper,
      final List<BalanceCalculatorStrategy> calculatorStrategies) {
    return new BalanceQueryService(
        driverQuery,
        qWeekQuery,
        transactionQuery,
        balanceLoadPort,
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
