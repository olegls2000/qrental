package ee.qrental.link.spring.config;

import ee.qrental.link.adapter.repository.LinkRepository;
import ee.qrental.link.repository.impl.LinkRepositoryImpl;
import ee.qrental.link.repository.spring.LinkSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkRepositoryConfig {

  @Bean
  LinkRepository getLinkRepository(final LinkSpringDataRepository springDataRepository) {
    return new LinkRepositoryImpl(springDataRepository);
  }
}
