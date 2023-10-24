package ee.qrental.car.core.service;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.car.api.in.request.CarLinkUpdateRequest;
import ee.qrental.car.api.in.response.CarLinkResponse;
import ee.qrental.car.api.out.CarLinkLoadPort;
import ee.qrental.car.core.mapper.CarLinkResponseMapper;
import ee.qrental.car.core.mapper.CarLinkUpdateRequestMapper;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CarLinkQueryService implements GetCarLinkQuery {

  private final CarLinkLoadPort loadPort;
  private final CarLinkResponseMapper mapper;
  private final CarLinkUpdateRequestMapper updateRequestMapper;

  @Override
  public List<CarLinkResponse> getAll() {
    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public CarLinkResponse getById(final Long id) {
    final var domain = loadPort.loadById(id);
    if (domain == null) {
      System.out.println(format("Link with id = %d was not found", id));
      return null;
    }
    return mapper.toResponse(domain);
  }

  @Override
  public String getObjectInfo(Long id) {
    final var domain = loadPort.loadById(id);
    if (domain == null) {
      System.out.println(format("Link with id = %d was not found", id));
      return null;
    }
    return mapper.toObjectInfo(domain);
  }

  @Override
  public CarLinkUpdateRequest getUpdateRequestById(Long id) {
    final var domain = loadPort.loadById(id);
    if (domain == null) {
      System.out.println(format("Link with id = %d was not found", id));
      return null;
    }
    return updateRequestMapper.toRequest(domain);
  }

  @Override
  public CarLinkResponse getActiveLinkByDriverId(final Long driverId) {
    final var domain = loadPort.loadActiveByDriverId(driverId);
    if (domain == null) {
      System.out.println(format("Active Link for driver with id = %d was not found", driverId));
      return null;
    }
    return mapper.toResponse(domain);
  }

  @Override
  public List<CarLinkResponse> getActiveByDate(final LocalDate date) {

    return loadPort.loadActiveByDate(date).stream()
        .map(mapper::toResponse)
        .collect(toList());
  }
}
