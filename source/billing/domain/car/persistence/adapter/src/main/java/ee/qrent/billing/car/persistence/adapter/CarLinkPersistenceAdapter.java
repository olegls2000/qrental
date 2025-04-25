package ee.qrent.billing.car.persistence.adapter;

import ee.qrent.billing.car.persistence.mapper.CarLinkAdapterMapper;
import ee.qrent.billing.car.persistence.repository.CarLinkRepository;
import ee.qrent.billing.car.api.out.CarLinkAddPort;
import ee.qrent.billing.car.api.out.CarLinkDeletePort;
import ee.qrent.billing.car.api.out.CarLinkUpdatePort;
import ee.qrent.billing.car.domain.CarLink;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CarLinkPersistenceAdapter implements CarLinkAddPort, CarLinkUpdatePort, CarLinkDeletePort {

  private final CarLinkRepository repository;
  private final CarLinkAdapterMapper mapper;

  @Override
  public CarLink add(final CarLink domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public CarLink update(final CarLink domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
