package ee.qrental.contract.core.mapper;


import ee.qrental.common.core.in.mapper.AddRequestMapper;
import ee.qrental.contract.api.in.request.AuthorizationBoltAddRequest;
import ee.qrental.contract.domain.AuthorizationBolt;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationBoltAddRequestMapper
    implements AddRequestMapper<AuthorizationBoltAddRequest, AuthorizationBolt> {

  private final GetDriverQuery driverQuery;

  @Override
  public AuthorizationBolt toDomain(AuthorizationBoltAddRequest request) {
    final var driverId = request.getDriverId();
    final var driver = driverQuery.getById(driverId);

    return AuthorizationBolt.builder()
        .id(null)
        .driverIsikukood(driver.getIsikukood())
        .driverFirstName(driver.getFirstName())
        .driverLastName(driver.getLastName())
        .created(LocalDate.now())
        .build();
  }
}
