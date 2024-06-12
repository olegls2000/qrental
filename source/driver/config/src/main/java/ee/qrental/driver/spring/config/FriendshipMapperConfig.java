package ee.qrental.driver.spring.config;

import ee.qrental.driver.core.mapper.FriendshipDomainMapper;
import ee.qrental.driver.core.mapper.FriendshipResponseMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FriendshipMapperConfig {
  @Bean
  FriendshipDomainMapper getFriendshipDomainMapper() {
    return new FriendshipDomainMapper();
  }

  @Bean
  FriendshipResponseMapper getFriendshipResponseMapper() {
    return new FriendshipResponseMapper();
  }
}
