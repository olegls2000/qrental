package ee.qrental.driver.adapter.mapper;

import ee.qrental.common.core.out.mapper.DomainMapper;
import ee.qrental.driver.domain.CallSignLink;
import ee.qrental.driver.entity.jakarta.CallSignLinkJakartaEntity;
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
        .driverId(entity.getDriverId())
        .callSign(callSignAdapterMapper.mapToDomain(entity.getCallSign()))
        .dateStart(entity.getDateStart())
        .dateEnd(entity.getDateEnd())
        .comment(entity.getComment())
        .build();
  }

  @Override
  public CallSignLinkJakartaEntity mapToEntity(final CallSignLink domain) {

    return CallSignLinkJakartaEntity.builder()
        .id(domain.getId())
        .driverId(domain.getDriverId())
        .callSign(callSignAdapterMapper.mapToEntity(domain.getCallSign()))
        .dateStart(domain.getDateStart())
        .dateEnd(domain.getDateEnd())
        .comment(domain.getComment())
        .build();
  }
}
