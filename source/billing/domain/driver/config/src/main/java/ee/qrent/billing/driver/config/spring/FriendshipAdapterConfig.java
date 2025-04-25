package ee.qrent.billing.driver.config.spring;

import ee.qrent.billing.driver.persistence.adapter.FriendshipLoadAdapter;
import ee.qrent.billing.driver.persistence.adapter.FriendshipPersistenceAdapter;
import ee.qrent.billing.driver.persistence.mapper.FriendshipAdapterMapper;
import ee.qrent.billing.driver.persistence.repository.FriendshipRepository;
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
