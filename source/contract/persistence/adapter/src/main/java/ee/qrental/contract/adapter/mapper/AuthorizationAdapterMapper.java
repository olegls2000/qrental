package ee.qrental.contract.adapter.mapper;

import ee.qrental.contract.domain.Authorization;
import ee.qrental.contract.entity.jakarta.AuthorizationJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationAdapterMapper {

  public Authorization mapToDomain(final AuthorizationJakartaEntity entity) {
    if (entity == null) {

      return null;
    }

    return Authorization.builder()
        .id(entity.getId())
        .driverIsikukood(entity.getDriverIsikukood())
        .driverFirstName(entity.getDriverFirstName())
        .driverLastName(entity.getDriverLastName())
        .driverEmail(entity.getDriverEmail())
        .driverId(entity.getDriverId())
        .created(entity.getCreated())
        .build();
  }

  public AuthorizationJakartaEntity mapToEntity(final Authorization domain) {

    return AuthorizationJakartaEntity.builder()
        .id(domain.getId())
        .driverId(domain.getDriverId())
        .driverIsikukood(domain.getDriverIsikukood())
        .driverFirstName(domain.getDriverFirstName())
        .driverLastName(domain.getDriverLastName())
        .driverEmail(domain.getDriverEmail())
        .created(domain.getCreated())
        .build();
  }
}
