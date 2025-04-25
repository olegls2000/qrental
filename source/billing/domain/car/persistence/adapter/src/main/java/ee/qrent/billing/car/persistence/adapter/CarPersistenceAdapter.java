package ee.qrent.billing.car.persistence.adapter;

import ee.qrent.billing.car.persistence.mapper.CarAdapterMapper;
import ee.qrent.billing.car.persistence.repository.CarRepository;
import ee.qrent.billing.car.api.out.CarAddPort;
import ee.qrent.billing.car.api.out.CarDeletePort;
import ee.qrent.billing.car.api.out.CarUpdatePort;
import ee.qrent.billing.car.domain.Car;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CarPersistenceAdapter implements CarAddPort, CarUpdatePort, CarDeletePort {

  private final CarRepository repository;
  private final CarAdapterMapper mapper;

  @Override
  public Car add(final Car domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public Car update(final Car domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
