package ee.qrental.link.adapter.mapper;

import ee.qrental.link.domain.Link;
import ee.qrental.link.entity.jakarta.LinkJakartaEntity;

public class LinkAdapterMapper {

  public Link mapToDomain(final LinkJakartaEntity entity) {
    return Link.builder()
            .id(entity.getId())
            .carId(entity.getCarId())
            .driverId(entity.getDriverId())
            .dateStart(entity.getDateStart())
            .dateEnd(entity.getDateEnd())
            .comment(entity.getComment())
            .build();
  }

  public LinkJakartaEntity mapToEntity(final Link domain) {
    return LinkJakartaEntity.builder()
            .id(domain.getId())
            .carId(domain.getCarId())
            .driverId(domain.getDriverId())
            .dateStart(domain.getDateStart())
            .dateEnd(domain.getDateEnd())
            .comment(domain.getComment())
            .build();
  }
}
