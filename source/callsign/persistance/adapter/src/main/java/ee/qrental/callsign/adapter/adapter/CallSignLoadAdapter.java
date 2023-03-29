package ee.qrental.callsign.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.callsign.adapter.mapper.CallSignAdapterMapper;
import ee.qrental.callsign.adapter.repository.CallSignRepository;
import ee.qrental.callsign.api.out.CallSignLoadPort;
import ee.qrental.callsign.domain.CallSign;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignLoadAdapter implements CallSignLoadPort {

  private final CallSignRepository repository;
  private final CallSignAdapterMapper mapper;

  @Override
  public List<CallSign> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public CallSign loadById(Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public CallSign loadByCallSign(final Integer callSign) {
    return mapper.mapToDomain(repository.findByCallSign(callSign));
  }
}
