package ee.qrental.car.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.car.adapter.mapper.CarAdapterMapper;
import ee.qrental.car.adapter.repository.CarRepository;
import ee.qrental.car.api.out.CarLoadPort;
import ee.qrental.car.domain.Car;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

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

  @Override
  public List<Car> loadNotAvailableByDate(final LocalDate date) {
    return repository.findNotAvailableByDate(date).stream()
        .map(mapper::mapToDomain)
        .collect(toList());
  }

  @Override
  public List<Car> loadByActive(boolean active) {
    return repository.findByActive(active).stream().map(mapper::mapToDomain).collect(toList());
  }
}
