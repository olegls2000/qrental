package ee.qrental.contract.core.mapper;

import static java.lang.String.format;

import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrental.contract.api.in.response.AuthorizationResponse;
import ee.qrental.contract.domain.Authorization;

public class AuthorizationResponseMapper
    implements ResponseMapper<AuthorizationResponse, Authorization> {
  @Override
  public AuthorizationResponse toResponse(final Authorization domain) {
    if (domain == null) {
      return null;
    }
    return AuthorizationResponse.builder()
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
  public String toObjectInfo(final Authorization domain) {
    return format(
        "Authorization Bolt for Driver: %d, %s %s",
        domain.getDriverIsikukood(), domain.getDriverFirstName(), domain.getDriverLastName());
  }
}
