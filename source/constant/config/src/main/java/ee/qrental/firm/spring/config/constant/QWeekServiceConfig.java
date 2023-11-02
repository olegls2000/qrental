package ee.qrental.firm.spring.config.constant;

import ee.qrental.constant.api.in.query.qweek.GetQWeekQuery;
import ee.qrental.constant.api.out.qweek.QWeekAddPort;
import ee.qrental.constant.api.out.qweek.QWeekDeletePort;
import ee.qrental.constant.api.out.qweek.QWeekLoadPort;
import ee.qrental.constant.api.out.qweek.QWeekUpdatePort;
import ee.qrental.constant.core.mapper.*;
import ee.qrental.constant.core.service.QWeekQueryService;
import ee.qrental.constant.core.service.QWeekUseCaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QWeekServiceConfig {

  @Bean
  public GetQWeekQuery getQWeekQueryServie(
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
      final QWeekUpdateRequestMapper updateRequestMapper) {
    return new QWeekUseCaseService(
        addPort, updatePort, deletePort, loadPort, addRequestMapper, updateRequestMapper);
  }
}
