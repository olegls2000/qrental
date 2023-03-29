package ee.qrental.callsign.spring.config.link;

import ee.qrental.callsign.api.out.*;
import ee.qrental.callsign.core.mapper.*;
import ee.qrental.callsign.core.service.CallSignLinkQueryService;
import ee.qrental.callsign.core.service.CallSignLinkUseCaseService;
import ee.qrental.callsign.core.validator.CallSignLinkBusinessRuleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallSignLinkServiceConfig {

  @Bean
  CallSignLinkQueryService getCallSignLinkQueryService(
      final CallSignLinkLoadPort loadPort,
      final CallSignLinkResponseMapper mapper,
      final CallSignLinkUpdateRequestMapper updateRequestMapper) {
    return new CallSignLinkQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  CallSignLinkUseCaseService getCallSignLinkUseCaseService(
      final CallSignLinkAddPort addPort,
      final CallSignLinkUpdatePort updatePort,
      final CallSignLinkDeletePort deletePort,
      final CallSignLinkLoadPort loadPort,
      final CallSignLinkAddRequestMapper addRequestMapper,
      final CallSignLinkUpdateRequestMapper updateRequestMapper,
      final CallSignLinkBusinessRuleValidator businessRuleValidator) {
    return new CallSignLinkUseCaseService(
        addPort,
        updatePort,
        deletePort,
        loadPort,
        addRequestMapper,
        updateRequestMapper,
        businessRuleValidator);
  }
}
