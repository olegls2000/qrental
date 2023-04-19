package ee.qrental.firm.spring.config;

import ee.qrental.firm.api.in.query.GetFirmQuery;
import ee.qrental.firm.api.out.FirmAddPort;
import ee.qrental.firm.api.out.FirmDeletePort;
import ee.qrental.firm.api.out.FirmLoadPort;
import ee.qrental.firm.api.out.FirmUpdatePort;
import ee.qrental.firm.core.mapper.FirmAddRequestMapper;
import ee.qrental.firm.core.mapper.FirmResponseMapper;
import ee.qrental.firm.core.mapper.FirmUpdateRequestMapper;
import ee.qrental.firm.core.service.FirmQueryService;
import ee.qrental.firm.core.service.FirmUseCaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirmServiceConfig {

  @Bean
  GetFirmQuery getFirmQueryService(
      final FirmLoadPort loadPort,
      final FirmResponseMapper mapper,
      final FirmUpdateRequestMapper updateRequestMapper) {
    return new FirmQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  public FirmUseCaseService getFirmUseCaseService(
      final FirmAddPort addPort,
      final FirmUpdatePort updatePort,
      final FirmDeletePort deletePort,
      final FirmLoadPort loadPort,
      final FirmAddRequestMapper addRequestMapper,
      final FirmUpdateRequestMapper updateRequestMapper) {
    return new FirmUseCaseService(
        addPort, updatePort, deletePort, loadPort, addRequestMapper, updateRequestMapper);
  }
}
