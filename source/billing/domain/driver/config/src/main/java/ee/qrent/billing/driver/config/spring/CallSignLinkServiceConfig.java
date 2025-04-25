package ee.qrent.billing.driver.config.spring;

import ee.qrent.billing.driver.api.out.*;
import ee.qrent.billing.driver.core.mapper.*;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrent.billing.driver.api.in.query.GetFirmLinkQuery;
import ee.qrent.billing.driver.core.service.CallSignLinkQueryService;
import ee.qrent.billing.driver.core.service.CallSignLinkUseCaseService;
import ee.qrent.billing.driver.core.service.FirmLinkQueryService;
import ee.qrent.billing.driver.core.validator.CallSignLinkRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallSignLinkServiceConfig {

  @Bean
  GetCallSignLinkQuery getCallSignLinkQueryService(
      final CallSignLinkLoadPort loadPort,
      final CallSignLinkResponseMapper mapper,
      final CallSignLinkUpdateRequestMapper updateRequestMapper) {
    return new CallSignLinkQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  GetFirmLinkQuery getFirmLinkQueryService(
      final FirmLinkLoadPort loadPort,
      final FirmLinkResponseMapper mapper,
      final FirmLinkUpdateRequestMapper updateRequestMapper) {
    return new FirmLinkQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  CallSignLinkUseCaseService getCallSignLinkUseCaseService(
      final CallSignLinkAddPort addPort,
      final CallSignLinkUpdatePort updatePort,
      final CallSignLinkDeletePort deletePort,
      final CallSignLinkLoadPort loadPort,
      final CallSignLinkAddRequestMapper addRequestMapper,
      final CallSignLinkUpdateRequestMapper updateRequestMapper,
      final CallSignLinkRequestValidator requestValidator,
      final QDateTime qDateTime) {
    return new CallSignLinkUseCaseService(
        addPort,
        updatePort,
        deletePort,
        loadPort,
        addRequestMapper,
        updateRequestMapper,
            requestValidator,
        qDateTime);
  }
}
