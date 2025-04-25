package ee.qrent.billing.contract.config.spring;

import ee.qrent.billing.contract.api.out.AbsenceAddPort;
import ee.qrent.billing.contract.api.out.AbsenceDeletePort;
import ee.qrent.billing.contract.api.out.AbsenceLoadPort;
import ee.qrent.billing.contract.api.out.AbsenceUpdatePort;
import ee.qrent.billing.contract.core.mapper.AbsenceAddRequestMapper;
import ee.qrent.billing.contract.core.mapper.AbsenceResponseMapper;
import ee.qrent.billing.contract.core.mapper.AbsenceUpdateRequestMapper;
import ee.qrent.billing.contract.core.service.AbsenceQueryService;
import ee.qrent.billing.contract.core.service.AbsenceUseCaseService;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.DeleteRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.contract.api.in.query.GetAbsenceQuery;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.contract.api.in.request.AbsenceAddRequest;
import ee.qrent.billing.contract.api.in.request.AbsenceDeleteRequest;
import ee.qrent.billing.contract.api.in.request.AbsenceUpdateRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AbsenceServiceConfig {

  @Bean
  GetAbsenceQuery getAbsenceQueryService(
      final GetContractQuery contractQuery,
      final GetQWeekQuery qWeekQuery,
      final AbsenceLoadPort loadPort,
      final AbsenceResponseMapper mapper,
      final AbsenceUpdateRequestMapper updateRequestMapper,
      final QDateTime qDateTime) {
    return new AbsenceQueryService(
        contractQuery, qWeekQuery, loadPort, mapper, updateRequestMapper, qDateTime);
  }

  @Bean
  AbsenceUseCaseService getAbsenceUseCaseService(
      final AbsenceAddPort addPort,
      final AbsenceUpdatePort updatePort,
      final AbsenceDeletePort deletePort,
      final AbsenceAddRequestMapper addRequestMapper,
      final AbsenceUpdateRequestMapper updateRequestMapper,
      final AddRequestValidator<AbsenceAddRequest> addRequestValidator,
      final UpdateRequestValidator<AbsenceUpdateRequest> updateRequestValidator,
      DeleteRequestValidator<AbsenceDeleteRequest> deleteRequestValidator) {

    return new AbsenceUseCaseService(
        addPort,
        updatePort,
        deletePort,
        addRequestMapper,
        updateRequestMapper,
        addRequestValidator,
        updateRequestValidator,
        deleteRequestValidator);
  }
}
