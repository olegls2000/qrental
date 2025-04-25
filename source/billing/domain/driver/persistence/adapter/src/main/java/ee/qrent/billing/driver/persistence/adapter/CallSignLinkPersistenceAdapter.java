package ee.qrent.billing.driver.persistence.adapter;

import ee.qrent.billing.driver.persistence.mapper.CallSignLinkAdapterMapper;
import ee.qrent.billing.driver.persistence.repository.CallSignLinkRepository;
import ee.qrent.billing.driver.api.out.CallSignLinkAddPort;
import ee.qrent.billing.driver.api.out.CallSignLinkDeletePort;
import ee.qrent.billing.driver.api.out.CallSignLinkUpdatePort;
import ee.qrent.billing.driver.domain.CallSignLink;
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
