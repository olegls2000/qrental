package ee.qrent.billing.constant.config.spring;

import ee.qrent.billing.constant.api.in.query.GetConstantQuery;
import ee.qrent.billing.constant.api.out.ConstantAddPort;
import ee.qrent.billing.constant.api.out.ConstantDeletePort;
import ee.qrent.billing.constant.api.out.ConstantLoadPort;
import ee.qrent.billing.constant.api.out.ConstantUpdatePort;
import ee.qrent.billing.constant.core.mapper.ConstantAddRequestMapper;
import ee.qrent.billing.constant.core.mapper.ConstantResponseMapper;
import ee.qrent.billing.constant.core.mapper.ConstantUpdateRequestMapper;
import ee.qrent.billing.constant.core.service.ConstantQueryService;
import ee.qrent.billing.constant.core.service.ConstantUseCaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConstantServiceConfig {

  @Bean
  public GetConstantQuery getConstantQueryService(
      final ConstantLoadPort loadPort,
      final ConstantResponseMapper mapper,
      final ConstantUpdateRequestMapper updateRequestMapper) {
    return new ConstantQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  public ConstantUseCaseService getConstantUseCaseService(
      final ConstantAddPort addPort,
      final ConstantUpdatePort updatePort,
      final ConstantDeletePort deletePort,
      final ConstantLoadPort loadPort,
      final ConstantAddRequestMapper addRequestMapper,
      final ConstantUpdateRequestMapper updateRequestMapper) {
    return new ConstantUseCaseService(
        addPort, updatePort, deletePort, loadPort, addRequestMapper, updateRequestMapper);
  }
}
