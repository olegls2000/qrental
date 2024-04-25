package ee.qrental.car.core.service;

import static java.util.Arrays.stream;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.car.api.in.query.filter.CarFilter;
import ee.qrental.car.api.in.request.CarUpdateRequest;
import ee.qrental.car.api.in.response.CarResponse;
import ee.qrental.car.api.out.CarLinkLoadPort;
import ee.qrental.car.api.out.CarLoadPort;
import ee.qrental.car.core.mapper.CarResponseMapper;
import ee.qrental.car.core.mapper.CarUpdateRequestMapper;
import ee.qrental.car.domain.CarLink;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import ee.qrental.car.domain.CarStatus;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CarQueryService implements GetCarQuery {
  private final Comparator<CarResponse> DEFAULT_COMPARATOR = comparing(CarResponse::getRegNumber);
  private final Comparator<CarResponse> STATUS_COMPARATOR = comparing(CarResponse::getStatus);

  private final CarLoadPort loadPort;
  private final CarResponseMapper mapper;
  private final CarUpdateRequestMapper updateRequestMapper;
  private final CarLinkLoadPort carLinkLoadPort;

  @Override
  public List<CarResponse> getAll() {
    return loadPort.loadAll().stream()
        .map(mapper::toResponse)
        .sorted(DEFAULT_COMPARATOR)
        .collect(toList());
  }

  @Override
  public CarResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public CarUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }

  @Override
  public List<CarResponse> getAvailableCars() {
    final var activeState = true;
    final var allCars = loadPort.loadByActive(activeState);
    final var activeLinks = carLinkLoadPort.loadActive();
    if (activeLinks.isEmpty()) {

      return allCars.stream().map(mapper::toResponse).sorted(DEFAULT_COMPARATOR).collect(toList());
    }

    final var activeCarIds = activeLinks.stream().map(CarLink::getCarId).collect(toSet());
    final var notActiveCars =
        allCars.stream().filter(car -> !activeCarIds.contains(car.getId())).collect(toList());

    return notActiveCars.stream().map(car -> mapper.toResponse(car))
            .sorted(STATUS_COMPARATOR)
            .collect(toList());
  }

  public List<CarResponse> getNotAvailableCars() {

    return loadPort.loadNotAvailableByDate(LocalDate.now()).stream()
        .map(car -> mapper.toResponse(car))
        .sorted(DEFAULT_COMPARATOR)
        .collect(toList());
  }

  @Override
  public List<CarResponse> getAllByFilter(final CarFilter filterRequest) {

    switch (filterRequest.getState()) {
      case AVAILABLE -> {
        return getAvailableCars();
      }
      case WITH_LINKS -> {
        return getNotAvailableCars();
      }
    }

    return getAll();
  }

  @Override
  public Map<String, String> getAllStatuses() {
    return stream(CarStatus.values()).collect(toMap(Enum::name, CarStatus::getLabel));
  }
}
