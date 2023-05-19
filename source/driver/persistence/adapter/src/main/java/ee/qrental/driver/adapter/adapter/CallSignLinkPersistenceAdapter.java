package ee.qrental.driver.adapter.adapter;

import ee.qrental.driver.adapter.mapper.CallSignLinkAdapterMapper;
import ee.qrental.driver.adapter.repository.CallSignLinkRepository;
import ee.qrental.driver.api.out.CallSignLinkAddPort;
import ee.qrental.driver.api.out.CallSignLinkDeletePort;
import ee.qrental.driver.api.out.CallSignLinkUpdatePort;
import ee.qrental.driver.domain.CallSignLink;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignLinkPersistenceAdapter
    implements CallSignLinkAddPort, CallSignLinkUpdatePort, CallSignLinkDeletePort {

  private final CallSignLinkRepository repository;
  private final CallSignLinkAdapterMapper mapper;

  @Override
  public CallSignLink add(final CallSignLink domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public CallSignLink update(final CallSignLink domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public void delete(final Long id) {
    repository.deleteById(id);
  }
}
