package ee.qrent.billing.contract.core.mapper;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrent.billing.contract.api.in.request.AuthorizationAddRequest;
import ee.qrent.billing.contract.domain.Authorization;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationAddRequestMapper
    implements AddRequestMapper<AuthorizationAddRequest, Authorization> {

  private final GetDriverQuery driverQuery;

  @Override
  public Authorization toDomain(AuthorizationAddRequest request) {
    final var driverId = request.getDriverId();
    final var driver = driverQuery.getById(driverId);

    return Authorization.builder()
        .id(null)
        .driverId(driverId)
        .driverIsikukood(driver.getIsikukood())
        .driverFirstName(driver.getFirstName())
        .driverLastName(driver.getLastName())
        .driverEmail(driver.getEmail())
        .created(LocalDate.now())
        .build();
  }
}
