package ee.qrent.billing.bolt.config.spring;

import ee.qrent.billing.bolt.api.in.query.GetBoltStatisticsQuery;
import ee.qrent.billing.bolt.api.out.BoltStatisticsAddPort;
import ee.qrent.billing.bolt.api.out.BoltStatisticsDeletePort;
import ee.qrent.billing.bolt.api.out.BoltStatisticsLoadPort;
import ee.qrent.billing.bolt.api.out.BoltStatisticsUpdatePort;
import ee.qrent.billing.bolt.core.mapper.BoltStatisticsAddRequestMapper;
import ee.qrent.billing.bolt.core.mapper.BoltStatisticsResponseMapper;
import ee.qrent.billing.bolt.core.mapper.BoltStatisticsUpdateRequestMapper;
import ee.qrent.billing.bolt.core.service.BoltStatisticsQueryService;
import ee.qrent.billing.bolt.core.service.BoltStatisticsUseCaseService;
import ee.qrent.billing.bolt.core.validator.BoltStatisticsRequestValidator;
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
  public BoltStatisticsUseCaseService getBoltStatisticsUseCaseService(
      final BoltStatisticsAddPort addPort,
      final BoltStatisticsUpdatePort updatePort,
      final BoltStatisticsDeletePort deletePort,
      final BoltStatisticsLoadPort loadPort,
      final BoltStatisticsAddRequestMapper addRequestMapper,
      final BoltStatisticsUpdateRequestMapper updateRequestMapper,
      final BoltStatisticsRequestValidator requestValidator) {

    return new BoltStatisticsUseCaseService(
        addPort,
        updatePort,
        deletePort,
        loadPort,
        addRequestMapper,
        updateRequestMapper,
        requestValidator);
  }
}
