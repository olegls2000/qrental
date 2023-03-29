package ee.qrental.callsign.spring.config;

import ee.qrental.callsign.api.in.query.GetCallSignQuery;
import ee.qrental.callsign.api.out.CallSignAddPort;
import ee.qrental.callsign.api.out.CallSignDeletePort;
import ee.qrental.callsign.api.out.CallSignLoadPort;
import ee.qrental.callsign.api.out.CallSignUpdatePort;
import ee.qrental.callsign.core.mapper.CallSignAddRequestMapper;
import ee.qrental.callsign.core.mapper.CallSignResponseMapper;
import ee.qrental.callsign.core.mapper.CallSignUpdateRequestMapper;
import ee.qrental.callsign.core.service.CallSignQueryService;
import ee.qrental.callsign.core.service.CallSignUseCaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallSignServiceConfig {

  @Bean
  GetCallSignQuery getCallSignQueryService(
      final CallSignLoadPort loadPort,
      final CallSignResponseMapper mapper,
      final CallSignUpdateRequestMapper updateRequestMapper) {
    return new CallSignQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  CallSignUseCaseService getCallSignUseCaseService(
      final CallSignAddPort addPort,
      final CallSignUpdatePort updatePort,
      final CallSignDeletePort deletePort,
      final CallSignLoadPort loadPort,
      final CallSignAddRequestMapper addRequestMapper,
      final CallSignUpdateRequestMapper updateRequestMapper) {
    return new CallSignUseCaseService(
        addPort, updatePort, deletePort, loadPort, addRequestMapper, updateRequestMapper);
  }
}
