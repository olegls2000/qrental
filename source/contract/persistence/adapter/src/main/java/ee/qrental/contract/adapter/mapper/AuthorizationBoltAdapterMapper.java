package ee.qrental.contract.adapter.mapper;


import ee.qrental.contract.adapter.repository.AuthorizationBoltRepository;
import ee.qrental.contract.domain.AuthorizationBolt;
import ee.qrental.contract.entity.jakarta.AuthorizationBoltJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationBoltAdapterMapper {

  public AuthorizationBolt mapToDomain(final AuthorizationBoltJakartaEntity entity) {
    if (entity == null) {

      return null;
    }

    return AuthorizationBolt.builder()
        .id(entity.getId())
        .driverIsikukood(entity.getDriverIsikukood())
        .driverFirstName(entity.getDriverFirstName())
        .driverLastName(entity.getDriverLastName())
        .driverEmail(entity.getDriverEmail())
        .driverId(entity.getDriverId())
        .created(entity.getCreated())
        .build();
  }

  public AuthorizationBoltJakartaEntity mapToEntity(final AuthorizationBolt domain) {

    return AuthorizationBoltJakartaEntity.builder()
        .id(domain.getId())
        .driverId(domain.getDriverId())
        .driverIsikukood(domain.getDriverIsikukood())
        .driverFirstName(domain.getDriverFirstName())
        .driverLastName(domain.getDriverLastName())
        .driverEmail(domain.getDriverEmail()).created(domain.getCreated())
        .build();
  }
}
