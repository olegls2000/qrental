package ee.qrent.billing.driver.config.spring;

import ee.qrent.billing.driver.persistence.repository.FriendshipRepository;
import ee.qrent.billing.driver.persistence.repository.impl.FriendshipRepositoryImpl;
import ee.qrent.billing.driver.persistence.repository.spring.FriendshipSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FriendshipRepositoryConfig {

  @Bean
  FriendshipRepository getFriendshipRepository(
      final FriendshipSpringDataRepository springDataRepository) {
    return new FriendshipRepositoryImpl(springDataRepository);
  }
}
