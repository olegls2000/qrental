package ee.qrent.billing.contract.config.spring;

import ee.qrent.billing.contract.api.out.ContractAddPort;
import ee.qrent.billing.contract.api.out.ContractLoadPort;
import ee.qrent.billing.contract.api.out.ContractUpdatePort;
import ee.qrent.billing.contract.core.mapper.ContractAddRequestMapper;
import ee.qrent.billing.contract.core.mapper.ContractResponseMapper;
import ee.qrent.billing.contract.core.mapper.ContractUpdateRequestMapper;
import ee.qrent.billing.contract.core.service.*;
import ee.qrent.billing.contract.core.service.pdf.*;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.CloseRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.contract.api.in.request.ContractAddRequest;
import ee.qrent.billing.contract.api.in.request.ContractCloseRequest;
import ee.qrent.billing.contract.api.in.request.ContractUpdateRequest;
import ee.qrent.billing.contract.api.in.usecase.ContractCloseUseCase;
import ee.qrent.billing.contract.api.in.usecase.ContractPdfUseCase;
import ee.qrent.billing.contract.api.in.usecase.ContractSendByEmailUseCase;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.insurance.api.in.query.GetInsuranceCaseQuery;
import ee.qrent.billing.insurance.api.in.usecase.InsuranceCaseCloseUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.Arrays.asList;

@Configuration
public class ContractServiceConfig {

  @Bean
  GetContractQuery getContractQueryService(
      final GetQWeekQuery qWeekQuery,
      final ContractEndDateCalculator endDateCalculator,
      final ContractLoadPort loadPort,
      final ContractResponseMapper mapper,
      final ContractUpdateRequestMapper updateRequestMapper,
      final QDateTime qDateTime) {

    return new ContractQueryService(
        qWeekQuery, endDateCalculator, loadPort, mapper, updateRequestMapper, qDateTime);
  }

  @Bean
  ContractEndDateCalculator getContractEndDateCalculator(final QDateTime qDateTime) {

    return new ContractEndDateCalculator(qDateTime);
  }

  @Bean
  ContractAddUpdateUseCaseService getContractUseCaseService(
      final ContractAddPort addPort,
      final ContractUpdatePort updatePort,
      final ContractLoadPort loadPort,
      final ContractAddRequestMapper addRequestMapper,
      final AddRequestValidator<ContractAddRequest> addRequestValidator,
      final UpdateRequestValidator<ContractUpdateRequest> updateRequestValidator,
      final QDateTime qDateTime) {

    return new ContractAddUpdateUseCaseService(
        addPort,
        updatePort,
        loadPort,
        addRequestMapper,
        addRequestValidator,
        updateRequestValidator,
        qDateTime);
  }

  @Bean
  ContractCloseUseCase getContractCloseUseCaseService(
      final ContractLoadPort contractLoadPort,
      final ContractUpdatePort contractUpdatePort,
      final GetDriverQuery driverQuery,
      final GetInsuranceCaseQuery insuranceCaseQuery,
      final InsuranceCaseCloseUseCase insuranceCaseCloseUseCase,
      final CloseRequestValidator<ContractCloseRequest> closeRuleValidator,
      final QDateTime qDateTime) {

    return new ContractCloseUseCaseService(
        contractLoadPort,
        contractUpdatePort,
        driverQuery,
        insuranceCaseQuery,
        insuranceCaseCloseUseCase,
        closeRuleValidator,
        qDateTime);
  }

  @Bean
  List<ContractToPdfConversionStrategy> getContractToPdfConversionStrategies(
      final ContractLoadPort loadPort) {

    return asList(
        new ContractToPdfConversionStrategyBefore2025(),
        new ContractToPdfConversionStrategyAfter2024(),
        new ContractToPdfConversionStrategyNewDriver(loadPort));
  }

  @Bean
  ContractToPdfConverter getContractToPdfConverter(
      final List<ContractToPdfConversionStrategy> strategies) {

    return new ContractToPdfConverter(strategies);
  }

  @Bean
  ContractToPdfModelMapper getContractToPdfModelMapper() {

    return new ContractToPdfModelMapper();
  }

  @Bean
  ContractSendByEmailUseCase getContractSendByEmailUseCase(

      final ContractLoadPort invoiceLoadPort,
      final ContractPdfUseCase invoicePdfUseCase,
      final QueueEntryPushUseCase notificationQueuePushUseCase,
      final QDateTime qDateTime) {

    return new ContractSendByEmailService(
        invoiceLoadPort, invoicePdfUseCase, notificationQueuePushUseCase, qDateTime);
  }

  @Bean
  ContractPdfUseCase getContractPdfUseCase(
      final ContractLoadPort loadPort,
      final ContractToPdfConverter converter,
      final ContractToPdfModelMapper mapper) {

    return new ContractPdfUseCaseImpl(loadPort, converter, mapper);
  }
}
