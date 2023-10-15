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

    return mapper.mapToDomain(repository.findActiveByDriverIdAndNowDate(driverId, nowDate));
  }

  @Override
  public List<CarLink> loadActiveByCarId(final Long carId) {
    final var nowDate = LocalDate.now();

    return repository.findActiveByCarIdAndNowDate(carId, nowDate).stream()
        .map(mapper::mapToDomain)
        .collect(toList());
  }
}
