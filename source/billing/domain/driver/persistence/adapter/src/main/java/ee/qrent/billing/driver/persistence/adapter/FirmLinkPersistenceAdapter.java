package ee.qrent.billing.driver.persistence.adapter;

import ee.qrent.billing.driver.api.out.FirmLinkAddPort;
import ee.qrent.billing.driver.api.out.FirmLinkDeletePort;
import ee.qrent.billing.driver.api.out.FirmLinkUpdatePort;
import ee.qrent.billing.driver.persistence.mapper.FirmLinkAdapterMapper;
import ee.qrent.billing.driver.persistence.repository.FirmLinkRepository;
import ee.qrent.billing.driver.domain.FirmLink;
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
