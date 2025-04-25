package ee.qrent.billing.firm.persistence.adapter;

import ee.qrent.billing.firm.persistence.mapper.FirmAdapterMapper;
import ee.qrent.billing.firm.persistence.repository.FirmRepository;
import ee.qrent.billing.firm.api.out.FirmAddPort;
import ee.qrent.billing.firm.api.out.FirmDeletePort;
import ee.qrent.billing.firm.api.out.FirmUpdatePort;
import ee.qrent.billing.firm.domain.Firm;
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
