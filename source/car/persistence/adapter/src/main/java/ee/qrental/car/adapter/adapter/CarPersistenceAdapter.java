package ee.qrental.car.adapter.adapter;

import ee.qrental.car.adapter.mapper.CarAdapterMapper;
import ee.qrental.car.adapter.repository.CarRepository;
import ee.qrental.car.api.out.CarAddPort;
import ee.qrental.car.api.out.CarDeletePort;
import ee.qrental.car.api.out.CarUpdatePort;
import ee.qrental.car.domain.Car;
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
