package ee.qrental.callsign.adapter.mapper;

import ee.qrental.callsign.domain.CallSign;
import ee.qrental.callsign.entity.jakarta.CallSignJakartaEntity;

public class CallSignAdapterMapper {

  public CallSign mapToDomain(final CallSignJakartaEntity entity) {
    return CallSign.builder()
        .id(entity.getId())
        .callSign(entity.getCallSign())
        .comment(entity.getComment())
        .build();
  }

  public CallSignJakartaEntity mapToEntity(final CallSign domain) {
    return CallSignJakartaEntity.builder()
        .id(domain.getId())
        .callSign(domain.getCallSign())
        .comment(domain.getComment())
        .build();
  }
}
