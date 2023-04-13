package ee.qrental.firm.adapter.adapter;

import ee.qrental.firm.adapter.mapper.FirmAdapterMapper;
import ee.qrental.firm.adapter.repository.FirmRepository;
import ee.qrental.firm.api.out.FirmAddPort;
import ee.qrental.firm.api.out.FirmDeletePort;
import ee.qrental.firm.api.out.FirmUpdatePort;
import ee.qrental.firm.domain.Firm;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FirmPersistenceAdapter implements FirmAddPort, FirmUpdatePort, FirmDeletePort {

  private final FirmRepository repository;
  private final FirmAdapterMapper mapper;

  @Override
  public Firm add(final Firm domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public Firm update(final Firm domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
