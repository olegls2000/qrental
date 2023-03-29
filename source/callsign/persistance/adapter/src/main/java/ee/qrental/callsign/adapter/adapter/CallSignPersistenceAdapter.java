package ee.qrental.callsign.adapter.adapter;

import ee.qrental.callsign.adapter.mapper.CallSignAdapterMapper;
import ee.qrental.callsign.adapter.repository.CallSignRepository;
import ee.qrental.callsign.api.out.CallSignAddPort;
import ee.qrental.callsign.api.out.CallSignDeletePort;
import ee.qrental.callsign.api.out.CallSignUpdatePort;
import ee.qrental.callsign.domain.CallSign;
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
