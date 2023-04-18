package ee.qrental.link.spring.config;

import ee.qrental.link.core.mapper.LinkAddRequestMapper;
import ee.qrental.link.core.mapper.LinkResponseMapper;
import ee.qrental.link.core.mapper.LinkUpdateRequestMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkMapperConfig {
  @Bean
  LinkAddRequestMapper getLinkAddRequestMapper() {
    return new LinkAddRequestMapper();
  }

  @Bean
  LinkResponseMapper getLinkResponseMapper() {
    return new LinkResponseMapper();
  }

  @Bean
  LinkUpdateRequestMapper getLinkUpdateRequestMapper() {
    return new LinkUpdateRequestMapper();
  }
}
