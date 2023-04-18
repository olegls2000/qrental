package ee.qrental.link.spring.config;

import ee.qrental.link.adapter.adapter.LinkLoadAdapter;
import ee.qrental.link.adapter.adapter.LinkPersistenceAdapter;
import ee.qrental.link.adapter.mapper.LinkAdapterMapper;
import ee.qrental.link.adapter.repository.LinkRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkAdapterConfig {
  @Bean
  LinkAdapterMapper getLinkAdapterMapper() {
    return new LinkAdapterMapper();
  }

  @Bean
  LinkLoadAdapter getLinkLoadAdapter(
      final LinkRepository repository, final LinkAdapterMapper mapper) {
    return new LinkLoadAdapter(repository, mapper);
  }

  @Bean
  LinkPersistenceAdapter getLinkPersistenceAdapter(
      final LinkRepository repository, final LinkAdapterMapper mapper) {
    return new LinkPersistenceAdapter(repository, mapper);
  }
}
