package ee.qrental.contract.core.mapper;

import static java.lang.String.format;

import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.contract.api.in.response.AuthorizationBoltResponse;
import ee.qrental.contract.domain.AuthorizationBolt;

public class AuthorizationBoltResponseMapper
    implements ResponseMapper<AuthorizationBoltResponse, AuthorizationBolt> {
  @Override
  public AuthorizationBoltResponse toResponse(final AuthorizationBolt domain) {
    if (domain == null) {
      return null;
    }
    return AuthorizationBoltResponse.builder()
        .id(domain.getId())
        .driverId(domain.getDriverId())
        .driverIsikukood(domain.getDriverIsikukood())
        .driverFirstName(domain.getDriverFirstName())
        .driverLastName(domain.getDriverLastName())
        .driverEmail(domain.getDriverEmail())
        .created(domain.getCreated())
        .build();
  }

  @Override
  public String toObjectInfo(final AuthorizationBolt domain) {
    return format(
        "Authorization Bolt for Driver: %d, %s %s",
        domain.getDriverIsikukood(), domain.getDriverFirstName(), domain.getDriverLastName());
  }
}
