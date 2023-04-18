package ee.qrental.link.spring.config;

import ee.qrental.link.api.in.query.GetLinkQuery;
import ee.qrental.link.api.out.LinkAddPort;
import ee.qrental.link.api.out.LinkDeletePort;
import ee.qrental.link.api.out.LinkLoadPort;
import ee.qrental.link.api.out.LinkUpdatePort;
import ee.qrental.link.core.mapper.LinkAddRequestMapper;
import ee.qrental.link.core.mapper.LinkResponseMapper;
import ee.qrental.link.core.mapper.LinkUpdateRequestMapper;
import ee.qrental.link.core.service.LinkQueryService;
import ee.qrental.link.core.service.LinkUseCaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkServiceConfig {

  @Bean
  public GetLinkQuery getLinkQueryService(
      final LinkLoadPort loadPort,
      final LinkResponseMapper mapper,
      final LinkUpdateRequestMapper updateRequestMapper) {
    return new LinkQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  public LinkUseCaseService getLinkUseCaseService(
      final LinkAddPort addPort,
      final LinkUpdatePort updatePort,
      final LinkDeletePort deletePort,
      final LinkLoadPort loadPort,
      final LinkAddRequestMapper addRequestMapper,
      final LinkUpdateRequestMapper updateRequestMapper) {
    return new LinkUseCaseService(
        addPort, updatePort, deletePort, loadPort, addRequestMapper, updateRequestMapper);
  }
}
