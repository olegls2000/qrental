package ee.qrent.billing.driver.persistence.mapper;

import ee.qrent.common.out.mapper.DomainMapper;
import ee.qrent.billing.driver.domain.CallSignLink;
import ee.qrent.billing.driver.persistence.entity.jakarta.CallSignLinkJakartaEntity;
import ee.qrent.billing.driver.persistence.entity.jakarta.DriverJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignLinkAdapterMapper
    implements DomainMapper<CallSignLink, CallSignLinkJakartaEntity> {

  private final CallSignAdapterMapper callSignAdapterMapper;

  @Override
  public CallSignLink mapToDomain(final CallSignLinkJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return CallSignLink.builder()
        .id(entity.getId())
        .callSign(callSignAdapterMapper.mapToDomain(entity.getCallSign()))
        .driverId(entity.getDriver().getId())
        .dateStart(entity.getDateStart())
        .dateEnd(entity.getDateEnd())
        .comment(entity.getComment())
        .build();
  }

  @Override
  public CallSignLinkJakartaEntity mapToEntity(final CallSignLink domain) {

    return CallSignLinkJakartaEntity.builder()
        .id(domain.getId())
        .callSign(callSignAdapterMapper.mapToEntity(domain.getCallSign()))
        .driver(DriverJakartaEntity.builder().id(domain.getDriverId()).build())
        .dateStart(domain.getDateStart())
        .dateEnd(domain.getDateEnd())
        .comment(domain.getComment())
        .build();
  }
}
