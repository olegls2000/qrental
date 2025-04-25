package ee.qrent.billing.driver.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.bonus.api.in.query.GetObligationCalculationQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.driver.api.in.request.DriverUpdateRequest;
import ee.qrent.billing.driver.api.in.response.DriverResponse;
import ee.qrent.billing.driver.api.in.response.FriendshipResponse;
import ee.qrent.billing.driver.api.out.DriverLoadPort;
import ee.qrent.billing.driver.api.out.FriendshipLoadPort;
import ee.qrent.billing.driver.core.mapper.DriverResponseMapper;
import ee.qrent.billing.driver.core.mapper.DriverUpdateRequestMapper;
import java.util.Comparator;
import java.util.List;

import ee.qrent.billing.driver.core.mapper.FriendshipResponseMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DriverQueryService implements GetDriverQuery {

  private final GetObligationCalculationQuery obligationCalculationQuery;

  private final DriverLoadPort loadPort;
  private final DriverUpdateRequestMapper updateRequestMapper;
  private final DriverResponseMapper mapper;
  private final FriendshipLoadPort friendshipLoadPort;
  private final FriendshipResponseMapper friendshipResponseMapper;

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
  public List<FriendshipResponse> getFriendships(final Long driverId) {
    return friendshipLoadPort.loadByDriverId(driverId).stream()
        .map(friendshipResponseMapper::toResponse)
        .collect(toList());
  }

  @Override
  public List<DriverResponse> getDriversWithZeroMatchCountForLatestCalculation() {
    final var matchCount = Integer.valueOf(0);
    final var latestCalculatedQWeekId = obligationCalculationQuery.getLastCalculatedQWeekId();

   return loadPort.loadByMatchCountAndQWeekId(matchCount, latestCalculatedQWeekId).stream()
            .map(mapper::toResponse)
            .sorted(getCallSignOrLastNameComparator())
            .collect(toList());
  }
}
