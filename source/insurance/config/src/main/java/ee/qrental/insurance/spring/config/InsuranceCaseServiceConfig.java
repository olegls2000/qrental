package ee.qrental.insurance.spring.config;

import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.insurance.api.in.query.GetInsuranceCalculationQuery;
import ee.qrental.insurance.api.in.query.GetInsuranceCaseBalanceQuery;
import ee.qrental.insurance.api.in.query.GetInsuranceCaseQuery;
import ee.qrental.insurance.api.out.*;
import ee.qrental.insurance.core.mapper.*;
import ee.qrental.insurance.core.service.*;
import ee.qrental.insurance.core.service.balance.*;
import ee.qrental.insurance.core.validator.InsuranceCalculationAddBusinessRuleValidator;
import ee.qrental.insurance.core.validator.InsuranceCaseAddBusinessRuleValidator;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.transaction.api.in.query.rent.GetRentCalculationQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class InsuranceCaseServiceConfig {

  @Bean
  InsuranceCaseBalanceCalculator getInsuranceCaseBalanceCalculator(
      final InsuranceCaseBalanceLoadPort insuranceCaseBalanceLoadPort,
      final GetCarLinkQuery carLinkQuery,
      final GetTransactionQuery transactionQuery,
      final GetTransactionTypeQuery transactionTypeQuery,
      final InsuranceCaseBalanceDeriveService deriveService,
      final TransactionAddUseCase transactionAddUseCase) {
    return new InsuranceCaseBalanceCalculatorService(
        insuranceCaseBalanceLoadPort,
        carLinkQuery,
        transactionQuery,
        transactionTypeQuery,
        deriveService,
        transactionAddUseCase);
  }

  @Bean
  GetInsuranceCaseBalanceQuery getInsuranceCaseBalanceQuery(
      final GetQWeekQuery qWeekQuery,
      final InsuranceCaseBalanceLoadPort balanceLoadPort,
      final InsuranceCaseBalanceCalculator insuranceCaseBalanceCalculator,
      final InsuranceCaseLoadPort insuranceCaseLoadPort) {
    return new InsuranceCaseBalanceQueryService(
        qWeekQuery, balanceLoadPort, insuranceCaseBalanceCalculator, insuranceCaseLoadPort);
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
      final InsuranceCalculationResponseMapper responseMapper,
      final GetQWeekQuery qWeekQuery,
      final GetRentCalculationQuery rentCalculationQuery,
      final GetBalanceQuery balanceQuery) {

    return new InsuranceCalculationQueryService(
        loadPort, responseMapper, qWeekQuery, rentCalculationQuery, balanceQuery);
  }

  @Bean
  InsuranceCalculationUseCaseService getInsuranceCalculationUseCaseService(
      final InsuranceCaseLoadPort caseLoadPort,
      final InsuranceCalculationAddPort calculationAddPort,
      final InsuranceCalculationAddRequestMapper calculationAddRequestMapper,
      final GetQWeekQuery qWeekQuery,
      final InsuranceCaseBalanceCalculator insuranceCaseBalanceCalculator,
      final InsuranceCalculationAddBusinessRuleValidator addBusinessRuleValidator) {

    return new InsuranceCalculationUseCaseService(
        caseLoadPort,
        calculationAddPort,
        calculationAddRequestMapper,
        qWeekQuery,
        insuranceCaseBalanceCalculator,
        addBusinessRuleValidator);
  }

  @Bean
  InsuranceCaseBalanceDeriveService getInsuranceCaseBalanceDeriveService(
      final TransactionAddUseCase transactionAddUseCase,
      final GetTransactionTypeQuery transactionTypeQuery) {

    return new InsuranceCaseBalanceDeriveService(transactionAddUseCase, transactionTypeQuery);
  }
}
