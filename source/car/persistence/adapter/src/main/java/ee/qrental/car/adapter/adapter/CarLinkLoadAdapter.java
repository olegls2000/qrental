package ee.qrental.car.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.car.adapter.mapper.CarLinkAdapterMapper;
import ee.qrental.car.adapter.repository.CarLinkRepository;
import ee.qrental.car.api.out.CarLinkLoadPort;
import ee.qrental.car.domain.CarLink;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CarLinkLoadAdapter implements CarLinkLoadPort {

  private final CarLinkRepository repository;
  private final CarLinkAdapterMapper mapper;

  @Override
  public List<CarLink> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public CarLink loadById(final Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public CarLink loadActiveByDriverId(final Long driverId) {
    final var nowDate = LocalDate.now();

    return mapper.mapToDomain(repository.findActiveByDriverIdAndDate(driverId, nowDate));
  }

  @Override
  public CarLink loadFirstByDriverId(Long driverId) {

    return mapper.mapToDomain(repository.findFirstByDriverId(driverId));
  }

  @Override
  public List<CarLink> loadActive() {
    final var nowDate = LocalDate.now();

    return repository.findActiveByDate(nowDate).stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public List<CarLink> loadActiveByDate(final LocalDate date) {
    return repository.findActiveByDate(date).stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Long loadCountActiveByDate(final LocalDate date) {
    return repository.findCountActiveByDate(date);
  }

  @Override
  public List<CarLink> loadClosedByDate(LocalDate date) {
    return repository.findClosedByDate(date).stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Long loadCountClosedByDate(final LocalDate date) {
    return repository.findCountClosedByDate(date);
  }

  @Override
  public List<CarLink> loadActiveByCarId(final Long carId) {
    final var nowDate = LocalDate.now();

    return repository.findActiveByCarIdAndDate(carId, nowDate).stream()
        .map(mapper::mapToDomain)
        .collect(toList());
  }
}
