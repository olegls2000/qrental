package ee.qrental.link.adapter.adapter;

import ee.qrental.link.adapter.mapper.LinkAdapterMapper;
import ee.qrental.link.adapter.repository.LinkRepository;
import ee.qrental.link.api.out.LinkAddPort;
import ee.qrental.link.api.out.LinkDeletePort;
import ee.qrental.link.api.out.LinkUpdatePort;
import ee.qrental.link.domain.Link;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LinkPersistenceAdapter implements LinkAddPort, LinkUpdatePort, LinkDeletePort {

  private final LinkRepository repository;
  private final LinkAdapterMapper mapper;

  @Override
  public Link add(final Link domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public Link update(final Link domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
