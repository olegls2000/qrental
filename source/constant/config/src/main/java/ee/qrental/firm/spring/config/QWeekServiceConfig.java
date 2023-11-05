package ee.qrental.firm.spring.config;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.out.QWeekAddPort;
import ee.qrental.constant.api.out.QWeekDeletePort;
import ee.qrental.constant.api.out.QWeekLoadPort;
import ee.qrental.constant.api.out.QWeekUpdatePort;
import ee.qrental.constant.core.mapper.*;
import ee.qrental.constant.core.service.QWeekQueryService;
import ee.qrental.constant.core.service.QWeekUseCaseService;
import ee.qrental.constant.core.validator.QWeekAddBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QWeekServiceConfig {

  @Bean
  public GetQWeekQuery getQWeekQueryService(
      final QWeekLoadPort loadPort,
      final QWeekResponseMapper mapper,
      final QWeekUpdateRequestMapper updateRequestMapper) {
    return new QWeekQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  public QWeekUseCaseService getQWeekUseCaseService(
      final QWeekAddPort addPort,
      final QWeekUpdatePort updatePort,
      final QWeekDeletePort deletePort,
      final QWeekLoadPort loadPort,
      final QWeekAddRequestMapper addRequestMapper,
      final QWeekUpdateRequestMapper updateRequestMapper,
      final QWeekAddBusinessRuleValidator addBusinessRuleValidator) {
    return new QWeekUseCaseService(
        addPort, updatePort, deletePort, loadPort, addRequestMapper, updateRequestMapper, addBusinessRuleValidator);
  }
}
