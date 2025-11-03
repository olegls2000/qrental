package ee.qrent.billing.bolt.config.spring;

import ee.qrent.billing.bolt.api.in.query.GetBoltRidesCountQuery;
import ee.qrent.billing.bolt.api.in.query.GetBoltStatisticsQuery;
import ee.qrent.billing.bolt.api.out.*;
import ee.qrent.billing.bolt.core.mapper.BoltRidesCountResponseMapper;
import ee.qrent.billing.bolt.core.mapper.BoltStatisticsAddRequestMapper;
import ee.qrent.billing.bolt.core.mapper.BoltStatisticsResponseMapper;
import ee.qrent.billing.bolt.core.mapper.BoltStatisticsUpdateRequestMapper;
import ee.qrent.billing.bolt.core.service.BoltOrdersCountQueryService;
import ee.qrent.billing.bolt.core.service.BoltStatisticsQueryService;
import ee.qrent.billing.bolt.core.service.BoltStatisticsUseCaseService;
import ee.qrent.billing.bolt.core.validator.BoltStatisticsRequestValidator;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BoltStatisticsServiceConfig {

  @Bean
  GetBoltStatisticsQuery getBoltStatisticsQueryService(
      final BoltStatisticsLoadPort loadPort,
      final BoltStatisticsResponseMapper mapper,
      final BoltStatisticsUpdateRequestMapper updateRequestMapper) {

    return new BoltStatisticsQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  GetBoltRidesCountQuery getBoltOrdersCountQueryService(
      final BoltRidesCountLoadPort loadPort, final BoltRidesCountResponseMapper mapper) {

    return new BoltOrdersCountQueryService(loadPort, mapper);
  }

  @Bean
  public BoltStatisticsUseCaseService getBoltStatisticsUseCaseService(
      final BoltStatisticsAddPort addPort,
      final BoltStatisticsUpdatePort updatePort,
      final BoltStatisticsDeletePort deletePort,
      final BoltStatisticsLoadPort loadPort,
      final BoltStatisticsAddRequestMapper addRequestMapper,
      final BoltStatisticsUpdateRequestMapper updateRequestMapper,
      final BoltStatisticsRequestValidator requestValidator,
      final BoltOrdersCountAddPort boltOrdersCountAddPort,
      final GetDriverQuery driverQuery,
      final GetQWeekQuery qWeekQuery) {

    return new BoltStatisticsUseCaseService(
        addPort,
        updatePort,
        deletePort,
        addRequestMapper,
        updateRequestMapper,
        boltOrdersCountAddPort,
        driverQuery,
        qWeekQuery);
  }
}
