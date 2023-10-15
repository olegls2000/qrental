package ee.qrental.car.adapter.mapper;

import ee.qrental.car.domain.CarLink;
import ee.qrental.car.entity.jakarta.CarLinkJakartaEntity;

public class CarLinkAdapterMapper {

  public CarLink mapToDomain(final CarLinkJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return CarLink.builder()
        .id(entity.getId())
        .carId(entity.getCarId())
        .driverId(entity.getDriverId())
        .dateStart(entity.getDateStart())
        .dateEnd(entity.getDateEnd())
        .comment(entity.getComment())
        .build();
  }

  public CarLinkJakartaEntity mapToEntity(final CarLink domain) {
    return CarLinkJakartaEntity.builder()
        .id(domain.getId())
        .carId(domain.getCarId())
        .driverId(domain.getDriverId())
        .dateStart(domain.getDateStart())
        .dateEnd(domain.getDateEnd())
        .comment(domain.getComment())
        .build();
  }
}
