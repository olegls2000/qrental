package ee.qrent.billing.driver.config.spring;

import ee.qrent.billing.driver.api.out.*;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.DeleteRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.billing.bonus.api.in.query.GetObligationCalculationQuery;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.contract.api.in.usecase.ContractUpdateUseCase;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.driver.api.in.request.DriverAddRequest;
import ee.qrent.billing.driver.api.in.request.DriverDeleteRequest;
import ee.qrent.billing.driver.api.in.request.DriverUpdateRequest;
import ee.qrent.billing.driver.core.mapper.DriverAddRequestMapper;
import ee.qrent.billing.driver.core.mapper.DriverResponseMapper;
import ee.qrent.billing.driver.core.mapper.DriverUpdateRequestMapper;
import ee.qrent.billing.driver.core.mapper.FriendshipResponseMapper;
import ee.qrent.billing.driver.core.service.DriverQueryService;
import ee.qrent.billing.driver.core.service.DriverUseCaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverServiceConfig {

  @Bean
  GetDriverQuery getDriverQueryService(
      final GetObligationCalculationQuery obligationCalculationQuery,
      final DriverLoadPort loadPort,
      final DriverUpdateRequestMapper updateRequestMapper,
      final DriverResponseMapper mapper,
      final FriendshipLoadPort friendshipLoadPort,
      final FriendshipResponseMapper friendshipResponseMapper) {
    return new DriverQueryService(
        obligationCalculationQuery,
        loadPort,
        updateRequestMapper,
        mapper,
        friendshipLoadPort,
        friendshipResponseMapper);
  }

  @Bean
  public DriverUseCaseService getDriverUseCaseService(
      final GetContractQuery contractQuery,
      final ContractUpdateUseCase updateUseCase,
      final DriverAddPort addPort,
      final DriverUpdatePort updatePort,
      final DriverDeletePort deletePort,
      final DriverLoadPort loadPort,
      final DriverAddRequestMapper addRequestMapper,
      final DriverUpdateRequestMapper updateRequestMapper,
      final AddRequestValidator<DriverAddRequest> addRequestValidator,
      final UpdateRequestValidator<DriverUpdateRequest> updateRequestValidator,
      final DeleteRequestValidator<DriverDeleteRequest> deleteRequestValidator) {

    return new DriverUseCaseService(
        contractQuery,
        updateUseCase,
        addPort,
        updatePort,
        deletePort,
        loadPort,
        addRequestMapper,
        updateRequestMapper,
        addRequestValidator,
        updateRequestValidator,
            deleteRequestValidator);
  }
}
