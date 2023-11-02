package ee.qrental.firm.spring.config.constant;

import ee.qrental.constant.api.in.query.constant.GetConstantQuery;
import ee.qrental.constant.api.out.constant.ConstantAddPort;
import ee.qrental.constant.api.out.constant.ConstantDeletePort;
import ee.qrental.constant.api.out.constant.ConstantLoadPort;
import ee.qrental.constant.api.out.constant.ConstantUpdatePort;
import ee.qrental.constant.core.mapper.ConstantAddRequestMapper;
import ee.qrental.constant.core.mapper.ConstantResponseMapper;
import ee.qrental.constant.core.mapper.ConstantUpdateRequestMapper;
import ee.qrental.constant.core.service.ConstantQueryService;
import ee.qrental.constant.core.service.ConstantUseCaseService;
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
