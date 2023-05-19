package ee.qrental.driver.adapter.adapter;

import ee.qrental.driver.adapter.mapper.CallSignAdapterMapper;
import ee.qrental.driver.adapter.repository.CallSignRepository;
import ee.qrental.driver.api.out.CallSignAddPort;
import ee.qrental.driver.api.out.CallSignDeletePort;
import ee.qrental.driver.api.out.CallSignUpdatePort;
import ee.qrental.driver.domain.CallSign;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignPersistenceAdapter
    implements CallSignAddPort, CallSignUpdatePort, CallSignDeletePort {

  private final CallSignRepository repository;
  private final CallSignAdapterMapper mapper;

  @Override
  public CallSign add(final CallSign domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public CallSign update(final CallSign domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
