package ee.qrental.car.adapter.adapter;

import ee.qrental.car.adapter.mapper.CarLinkAdapterMapper;
import ee.qrental.car.adapter.repository.CarLinkRepository;
import ee.qrental.car.api.out.CarLinkAddPort;
import ee.qrental.car.api.out.CarLinkDeletePort;
import ee.qrental.car.api.out.CarLinkUpdatePort;
import ee.qrental.car.domain.CarLink;
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
