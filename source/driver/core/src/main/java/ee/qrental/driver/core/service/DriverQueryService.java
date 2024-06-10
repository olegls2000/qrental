package ee.qrental.driver.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.request.DriverUpdateRequest;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.driver.api.out.DriverLoadPort;
import ee.qrental.driver.core.mapper.DriverResponseMapper;
import ee.qrental.driver.core.mapper.DriverUpdateRequestMapper;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DriverQueryService implements GetDriverQuery {

  private final DriverLoadPort loadPort;
  private final DriverResponseMapper mapper;
  private final DriverUpdateRequestMapper updateRequestMapper;
  private static final int FRIENDSHIP_WEEK_COUNT = 4;

  @Override
  public List<DriverResponse> getAll() {

    return loadPort.loadAll().stream()
        .map(mapper::toResponse)
        .sorted(getCallSignOrLastNameComparator())
        .collect(toList());
  }

  @Override
  public DriverResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public DriverUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }

  private Comparator<DriverResponse> getCallSignOrLastNameComparator() {
    return (driver1, driver2) -> {
      final var callSign1 = driver1.getCallSign();
      final var callSign2 = driver2.getCallSign();
      if (callSign1 == null) {
        if (callSign2 == null) {
          return driver1.getLastName().compareToIgnoreCase(driver2.getLastName());
        }
        return 1;
      } else if (callSign2 == null) {
        return -1;
      }
      return callSign1.compareTo(callSign2);
    };
  }

  @Override
  public List<DriverResponse> getFriends(final Long driverId) {
    // TODO ..
    return List.of();
  }
}
