package ee.qrental.callsign.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.callsign.adapter.mapper.CallSignLinkAdapterMapper;
import ee.qrental.callsign.adapter.repository.CallSignLinkRepository;
import ee.qrental.callsign.api.out.CallSignLinkLoadPort;
import ee.qrental.callsign.domain.CallSignLink;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignLinkLoadAdapter implements CallSignLinkLoadPort {

  private final CallSignLinkRepository repository;
  private final CallSignLinkAdapterMapper mapper;

  @Override
  public List<CallSignLink> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public CallSignLink loadById(final Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public List<CallSignLink> loadByCallSignId(final Long callSignId) {
    return repository.findAllByCallSignId(callSignId).stream()
        .map(mapper::mapToDomain)
        .collect(toList());
  }

  @Override
  public CallSignLink loadActiveByCallSignId(final Long callSignId) {
    return mapper.mapToDomain(repository.findOneByDateEndIsNullAndCallSignId(callSignId));
  }

  @Override
  public CallSignLink loadActiveByDriverId(final Long driverId) {
    return mapper.mapToDomain(repository.findOneByDateEndIsNullAndDriverId(driverId));
  }
}
