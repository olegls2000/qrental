package ee.qrental.insurance.spring.config;

import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.insurance.api.in.query.GetInsuranceCaseBalanceQuery;
import ee.qrental.insurance.api.in.query.GetInsuranceCaseQuery;
import ee.qrental.insurance.api.out.*;
import ee.qrental.insurance.core.mapper.*;
import ee.qrental.insurance.core.service.*;
import ee.qrental.insurance.core.service.balance.*;
import ee.qrental.insurance.core.validator.InsuranceCaseAddBusinessRuleValidator;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

import static java.util.Arrays.asList;

@Configuration
@EnableTransactionManagement
public class InsuranceCaseServiceConfig {

  @Bean
  public List<InsuranceCaseBalanceCalculatorStrategy> getInsuranceCaseCalculatorStrategies(
      final GetCarLinkQuery carLinkQuery,
      final TransactionAddUseCase transactionAddUseCase,
      final GetTransactionQuery transactionQuery,
      final GetTransactionTypeQuery transactionTypeQuery,
      final InsuranceCaseBalanceDeriveService deriveService) {

    return asList(
        new InsuranceCaseBalanceCalculationStrategyDryRun(
            carLinkQuery, transactionQuery, transactionTypeQuery, deriveService),
        new InsuranceCaseBalanceCalculationStrategySaving(
            carLinkQuery,
            transactionAddUseCase,
            transactionQuery,
            transactionTypeQuery,
            deriveService));
  }

  @Bean
  GetInsuranceCaseBalanceQuery getInsuranceCaseBalanceQuery(
      final GetQWeekQuery qWeekQuery,
      final InsuranceCaseBalanceLoadPort balanceLoadPort,
      final InsuranceCaseLoadPort caseLoadPort,
      final InsuranceCalculationLoadPort calculationLoadPort,
      final List<InsuranceCaseBalanceCalculatorStrategy> calculatorStrategies) {
    return new InsuranceCaseBalanceQueryService(
        qWeekQuery, balanceLoadPort, caseLoadPort, calculationLoadPort, calculatorStrategies);
  }

  @Bean
  GetInsuranceCaseQuery getInsuranceCaseQueryService(
      final InsuranceCaseLoadPort loadPort,
      final InsuranceCaseBalanceLoadPort insuranceCaseBalanceLoadPort,
      final InsuranceCaseResponseMapper mapper,
      final InsuranceCaseBalanceResponseMapper insuranceCaseBalanceResponseMapper,
      final InsuranceCaseUpdateRequestMapper updateRequestMapper) {
    return new InsuranceCaseQueryService(
        loadPort,
        insuranceCaseBalanceLoadPort,
        mapper,
        insuranceCaseBalanceResponseMapper,
        updateRequestMapper);
  }

  @Bean
  InsuranceCaseUseCaseService getInsuranceCaseUseCaseService(
      final InsuranceCaseAddPort addPort,
      final InsuranceCaseUpdatePort updatePort,
      final InsuranceCaseDeletePort deletePort,
      final InsuranceCaseLoadPort loadPort,
      final InsuranceCaseAddRequestMapper addRequestMapper,
      final InsuranceCaseUpdateRequestMapper updateRequestMapper,
      final InsuranceCaseAddBusinessRuleValidator businessRuleValidator) {
    return new InsuranceCaseUseCaseService(
        addPort,
        updatePort,
        deletePort,
        loadPort,
        addRequestMapper,
        updateRequestMapper,
        businessRuleValidator);
  }

  @Bean
  InsuranceCalculationQueryService getInsuranceCalculationQueryService(
      final InsuranceCalculationLoadPort loadPort,
      final InsuranceCalculationResponseMapper responseMapper) {

    return new InsuranceCalculationQueryService(loadPort, responseMapper);
  }

  @Bean
  InsuranceCalculationUseCaseService getInsuranceCalculationUseCaseService(
      final InsuranceCaseLoadPort caseLoadPort,
      final InsuranceCaseBalanceLoadPort caseBalanceLodPort,
      final InsuranceCalculationLoadPort calculationLoadPort,
      final InsuranceCalculationAddPort calculationAddPort,
      final InsuranceCalculationAddRequestMapper calculationAddRequestMapper,
      final GetTransactionQuery transactionQuery,
      final TransactionAddUseCase transactionAddUseCase,
      final GetTransactionTypeQuery transactionTypeQuery,
      final GetQWeekQuery qWeekQuery,
      final List<InsuranceCaseBalanceCalculatorStrategy> calculatorStrategies) {

    return new InsuranceCalculationUseCaseService(
        caseLoadPort,
        caseBalanceLodPort,
        calculationLoadPort,
        calculationAddPort,
        calculationAddRequestMapper,
        transactionQuery,
        transactionTypeQuery,
        qWeekQuery,
        calculatorStrategies);
  }

  @Bean
  InsuranceCaseBalanceDeriveService getInsuranceCaseBalanceDeriveService(
      final TransactionAddUseCase transactionAddUseCase,
      final GetTransactionTypeQuery transactionTypeQuery) {

    return new InsuranceCaseBalanceDeriveService(transactionAddUseCase, transactionTypeQuery);
  }
}
