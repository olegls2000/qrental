package ee.qrental.driver.spring.config;

import ee.qrental.driver.adapter.repository.FriendshipRepository;
import ee.qrental.driver.repository.impl.FriendshipRepositoryImpl;
import ee.qrental.driver.repository.spring.FriendshipSpringDataRepository;
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
