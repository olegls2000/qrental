package ee.qrent.billing.insurance.config.spring;

import ee.qrent.billing.insurance.api.out.*;
import ee.qrent.billing.insurance.core.mapper.*;
import ee.qrent.billing.insurance.core.service.*;
import ee.qrent.billing.insurance.core.service.balance.*;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.CloseRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.insurance.api.in.query.GetInsuranceCalculationQuery;
import ee.qrent.billing.insurance.api.in.query.GetInsuranceCaseBalanceQuery;
import ee.qrent.billing.insurance.api.in.query.GetInsuranceCaseQuery;
import ee.qrent.billing.insurance.api.in.query.GetQKaskoQuery;
import ee.qrent.billing.insurance.api.in.request.InsuranceCalculationAddRequest;
import ee.qrent.billing.insurance.api.in.request.InsuranceCaseCloseRequest;
import ee.qrent.billing.insurance.api.in.request.InsuranceCaseUpdateRequest;
import ee.qrent.billing.insurance.api.in.usecase.InsuranceCaseCloseUseCase;
import ee.qrent.billing.insurance.core.service.kasko.QKaskoQueryService;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrent.billing.transaction.api.in.query.kind.GetTransactionKindQuery;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.billing.transaction.api.in.usecase.TransactionAddUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

import static java.util.Arrays.asList;

@Configuration
@EnableTransactionManagement
public class InsuranceCaseServiceConfig {

  @Bean
  List<InsuranceCaseBalanceCalculationStrategy> getInsuranceCaseBalanceCalculationStrategies(
      final GetQWeekQuery qWeekQuery,
      final GetQKaskoQuery qKaskoQuery,
      final GetTransactionTypeQuery transactionTypeQuery,
      final TransactionAddUseCase transactionAddUseCase,
      final InsuranceCaseBalanceLoadPort insuranceCaseBalanceLoadPort,
      final GetTransactionQuery transactionQuery,
      final GetInsuranceCaseQuery insuranceCaseQuery) {
    return asList(
        new InsuranceCaseBalanceWithQKaskoCalculationStrategy(
            qWeekQuery,
            qKaskoQuery,
            transactionTypeQuery,
            transactionAddUseCase,
            insuranceCaseBalanceLoadPort,
            transactionQuery),
        new InsuranceCaseBalanceWithoutQKaskoCalculationStrategy(
            qWeekQuery,
            qKaskoQuery,
            transactionTypeQuery,
            transactionAddUseCase,
            insuranceCaseBalanceLoadPort,
            insuranceCaseQuery));
  }

  @Bean
  InsuranceCaseBalanceCalculator getInsuranceCaseBalanceCalculator(
      List<InsuranceCaseBalanceCalculationStrategy> calculationStrategies) {

    return new InsuranceCaseBalanceCalculationService(calculationStrategies);
  }

  @Bean
  GetInsuranceCaseBalanceQuery getInsuranceCaseBalanceQuery(
      final GetQWeekQuery qWeekQuery,
      final InsuranceCaseBalanceLoadPort balanceLoadPort,
      final InsuranceCaseBalanceCalculator insuranceCaseBalanceCalculator,
      final InsuranceCaseLoadPort insuranceCaseLoadPort,
      final GetInsuranceCalculationQuery insuranceCalculationQuery,
      final GetTransactionQuery transactionQuery,
      final GetTransactionKindQuery transactionKindQuery) {

    return new InsuranceCaseBalanceQueryService(
        qWeekQuery,
        balanceLoadPort,
        insuranceCaseBalanceCalculator,
        insuranceCaseLoadPort,
        insuranceCalculationQuery,
        transactionQuery,
        transactionKindQuery);
  }

  @Bean
  GetInsuranceCaseQuery getInsuranceCaseQueryService(
      final GetTransactionQuery transactionQuery,
      final InsuranceCaseLoadPort loadPort,
      final InsuranceCaseBalanceLoadPort insuranceCaseBalanceLoadPort,
      final InsuranceCaseResponseMapper mapper,
      final InsuranceCaseBalanceResponseMapper insuranceCaseBalanceResponseMapper,
      final InsuranceCaseUpdateRequestMapper updateRequestMapper) {

    return new InsuranceCaseQueryService(
        transactionQuery,
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
      final InsuranceCaseAddRequestMapper addRequestMapper,
      final InsuranceCaseUpdateRequestMapper updateRequestMapper,
      final UpdateRequestValidator<InsuranceCaseUpdateRequest> updateRequestValidator) {

    return new InsuranceCaseUseCaseService(
        addPort, updatePort, addRequestMapper, updateRequestMapper, updateRequestValidator);
  }

  @Bean
  InsuranceCaseCloseUseCase getInsuranceCaseCloseUseCase(
      final InsuranceCaseUpdatePort updatePort,
      final InsuranceCaseLoadPort loadPort,
      final CloseRequestValidator<InsuranceCaseCloseRequest> closeRuleValidator,
      final GetQKaskoQuery getQKaskoQuery,
      final GetQWeekQuery qWeekQuery,
      final GetDriverQuery driverQuery,
      final GetTransactionQuery transactionQuery,
      final GetTransactionTypeQuery transactionTypeQuery,
      final QDateTime qDateTime,
      final TransactionAddUseCase transactionAddUseCase) {

    return new InsuranceCaseCloseUseCaseService(
        updatePort,
        loadPort,
        closeRuleValidator,
        getQKaskoQuery,
        qWeekQuery,
        driverQuery,
        transactionQuery,
        transactionTypeQuery,
        qDateTime,
        transactionAddUseCase);
  }

  @Bean
  InsuranceCalculationQueryService getInsuranceCalculationQueryService(
      final InsuranceCalculationLoadPort loadPort,
      final InsuranceCalculationResponseMapper responseMapper,
      final GetQWeekQuery qWeekQuery,
      final GetBalanceQuery balanceQuery) {

    return new InsuranceCalculationQueryService(loadPort, responseMapper, qWeekQuery, balanceQuery);
  }

  @Bean
  InsuranceCalculationUseCaseService getInsuranceCalculationUseCaseService(
      final InsuranceCaseLoadPort caseLoadPort,
      final InsuranceCaseUpdatePort caseUpdatePort,
      final InsuranceCalculationAddPort calculationAddPort,
      final InsuranceCalculationAddRequestMapper calculationAddRequestMapper,
      final GetQWeekQuery qWeekQuery,
      final InsuranceCaseBalanceCalculator insuranceCaseBalanceCalculator,
      final AddRequestValidator<InsuranceCalculationAddRequest> addRequestValidator) {

    return new InsuranceCalculationUseCaseService(
        caseLoadPort,
        caseUpdatePort,
        calculationAddPort,
        calculationAddRequestMapper,
        qWeekQuery,
        insuranceCaseBalanceCalculator,
        addRequestValidator);
  }

  @Bean
  GetQKaskoQuery getGetQKaskoQuery(final GetContractQuery contractQuery) {

    return new QKaskoQueryService(contractQuery);
  }
}
