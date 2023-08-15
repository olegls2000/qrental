package ee.qrental.driver.spring.config;

import ee.qrental.driver.adapter.adapter.CallSignHandler;
import ee.qrental.driver.adapter.adapter.DriverLoadAdapter;
import ee.qrental.driver.adapter.adapter.DriverPersistenceAdapter;
import ee.qrental.driver.adapter.adapter.FirmHandler;
import ee.qrental.driver.adapter.mapper.DriverAdapterMapper;
import ee.qrental.driver.adapter.repository.CallSignLinkRepository;
import ee.qrental.driver.adapter.repository.CallSignRepository;
import ee.qrental.driver.adapter.repository.DriverRepository;
import ee.qrental.driver.adapter.repository.FirmLinkRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverAdapterConfig {
  @Bean
  DriverAdapterMapper getDriverAdapterMapper(final CallSignLinkRepository callSignLinkRepository) {
    return new DriverAdapterMapper(callSignLinkRepository);
  }

  @Bean
  DriverLoadAdapter getDriverLoadAdapter(
      final DriverRepository repository, final DriverAdapterMapper mapper) {
    return new DriverLoadAdapter(repository, mapper);
  }

  @Bean
  CallSignHandler getCallSignHandler(
      final CallSignLinkRepository callSignLinkRepository,
      final CallSignRepository callSignRepository) {
    return new CallSignHandler(callSignLinkRepository, callSignRepository);
  }

  @Bean
  FirmHandler getFirmHandler(
          final FirmLinkRepository firmLinkRepository) {
    return new FirmHandler(firmLinkRepository);
  }

  @Bean
  DriverPersistenceAdapter getDriverPersistenceAdapter(
      final DriverRepository driverRepository,
      final DriverAdapterMapper driverAdapterMapper,
      final CallSignHandler callSignHandler,
      final FirmHandler firmHandler) {

    return new DriverPersistenceAdapter(driverRepository, driverAdapterMapper, callSignHandler, firmHandler);
  }
}
