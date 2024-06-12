package ee.qrental.driver.spring.config;

import ee.qrental.driver.adapter.adapter.FriendshipLoadAdapter;
import ee.qrental.driver.adapter.adapter.FriendshipPersistenceAdapter;
import ee.qrental.driver.adapter.mapper.FriendshipAdapterMapper;
import ee.qrental.driver.adapter.repository.FriendshipRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FriendshipAdapterConfig {
  @Bean
  FriendshipAdapterMapper getFriendshipAdapterMapper() {
    return new FriendshipAdapterMapper();
  }

  @Bean
  FriendshipPersistenceAdapter getFriendshipPersistenceAdapter(
      final FriendshipRepository friendshipRepository,
      final FriendshipAdapterMapper friendshipAdapterMapper) {

    return new FriendshipPersistenceAdapter(friendshipRepository, friendshipAdapterMapper);
  }

  @Bean
  FriendshipLoadAdapter getFriendshipLoadAdapter(
      final FriendshipRepository repository, final FriendshipAdapterMapper mapper) {
    return new FriendshipLoadAdapter(repository, mapper);
  }
}
