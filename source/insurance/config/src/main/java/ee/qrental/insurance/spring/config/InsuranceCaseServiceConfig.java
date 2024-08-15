package ee.qrental.insurance.spring.config;

import ee.qrent.common.in.time.QDateTime;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.insurance.api.in.query.GetInsuranceCaseQuery;
import ee.qrental.insurance.api.out.*;
import ee.qrental.insurance.core.mapper.*;
import ee.qrental.insurance.core.service.InsuranceCalculationQueryService;
import ee.qrental.insurance.core.service.InsuranceCalculationUseCaseService;
import ee.qrental.insurance.core.service.InsuranceCaseQueryService;
import ee.qrental.insurance.core.service.InsuranceCaseUseCaseService;
import ee.qrental.insurance.core.validator.InsuranceCaseAddBusinessRuleValidator;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class InsuranceCaseServiceConfig {

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
      final InsuranceCaseBalanceAddPort caseBalanceAddPort,
      final InsuranceCalculationLoadPort calculationLoadPort,
      final InsuranceCalculationAddPort calculationAddPort,
      final InsuranceCalculationAddRequestMapper calculationAddRequestMapper,
      final GetTransactionQuery transactionQuery,
      final TransactionAddUseCase transactionAddUseCase,
      final GetTransactionTypeQuery transactionTypeQuery,
      final GetQWeekQuery qWeekQuery,
      final QDateTime qDateTime) {

    return new InsuranceCalculationUseCaseService(
        caseLoadPort,
        caseBalanceLodPort,
        caseBalanceAddPort,
        calculationLoadPort,
        calculationAddPort,
        calculationAddRequestMapper,
        transactionQuery,
        transactionAddUseCase,
        transactionTypeQuery,
        qWeekQuery,
        qDateTime);
  }
}
