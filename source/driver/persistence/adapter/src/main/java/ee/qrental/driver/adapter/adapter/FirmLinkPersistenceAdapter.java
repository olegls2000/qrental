package ee.qrental.driver.adapter.adapter;

import ee.qrental.driver.adapter.mapper.FirmLinkAdapterMapper;
import ee.qrental.driver.adapter.repository.FirmLinkRepository;
import ee.qrental.driver.api.out.*;
import ee.qrental.driver.domain.FirmLink;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FirmLinkPersistenceAdapter
    implements FirmLinkAddPort, FirmLinkUpdatePort, FirmLinkDeletePort {

  private final FirmLinkRepository repository;
  private final FirmLinkAdapterMapper mapper;

  @Override
  public FirmLink add(final FirmLink domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public FirmLink update(final FirmLink domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public void delete(final Long id) {
    repository.deleteById(id);
  }
}
