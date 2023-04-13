package ee.qrental.car.adapter.adapter;

import ee.qrental.car.adapter.mapper.CarAdapterMapper;
import ee.qrental.car.adapter.repository.CarRepository;
import ee.qrental.car.api.out.CarLoadPort;
import ee.qrental.car.domain.Car;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class CarLoadAdapter implements CarLoadPort {

  private final CarRepository repository;
  private final CarAdapterMapper mapper;

  @Override
  public List<Car> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Car loadById(Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }
}
