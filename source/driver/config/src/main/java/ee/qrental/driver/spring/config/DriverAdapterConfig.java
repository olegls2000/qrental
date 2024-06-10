package ee.qrental.driver.spring.config;

import ee.qrental.driver.adapter.adapter.*;
import ee.qrental.driver.adapter.mapper.DriverAdapterMapper;
import ee.qrental.driver.adapter.mapper.FriendshipAdapterMapper;
import ee.qrental.driver.adapter.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverAdapterConfig {
  @Bean
  DriverAdapterMapper getDriverAdapterMapper(
      final CallSignLinkRepository callSignLinkRepository,
      final FriendshipRepository friendshipRepository,
      final FriendshipAdapterMapper friendshipAdapterMapper) {
    return new DriverAdapterMapper(
        callSignLinkRepository, friendshipRepository, friendshipAdapterMapper);
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
  FirmHandler getFirmHandler(final FirmLinkRepository firmLinkRepository) {
    return new FirmHandler(firmLinkRepository);
  }

  @Bean
  FriendshipHandler getFriendshipHandler(final FriendshipRepository repository) {
    return new FriendshipHandler(repository);
  }

  @Bean
  DriverPersistenceAdapter getDriverPersistenceAdapter(
      final DriverRepository driverRepository,
      final DriverAdapterMapper driverAdapterMapper,
      final CallSignHandler callSignHandler,
      final FirmHandler firmHandler,
      final FriendshipHandler friendshipHandler) {

    return new DriverPersistenceAdapter(
        driverRepository, driverAdapterMapper, callSignHandler, firmHandler, friendshipHandler);
  }
}
