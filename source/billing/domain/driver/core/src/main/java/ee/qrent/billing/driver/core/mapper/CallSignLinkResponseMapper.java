package ee.qrent.billing.driver.core.mapper;

import static java.lang.String.format;

import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.driver.api.in.request.CallSignLinkResponse;
import ee.qrent.billing.driver.domain.CallSignLink;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignLinkResponseMapper
    implements ResponseMapper<CallSignLinkResponse, CallSignLink> {

  private final GetDriverQuery driverQuery;

  @Override
  public CallSignLinkResponse toResponse(final CallSignLink domain) {
    if (domain == null) {
      return null;
    }
    return CallSignLinkResponse.builder()
        .id(domain.getId())
        .callSign(domain.getCallSign().getCallSign())
        .callSignId(domain.getCallSign().getId())
        .driverId(domain.getDriverId())
        .dateStart(domain.getDateStart())
        .dateEnd(domain.getDateEnd())
        .driverInfo(driverQuery.getObjectInfo(domain.getDriverId()))
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(CallSignLink domain) {
    final var driverInfo = driverQuery.getObjectInfo(domain.getDriverId());
    final var callSign = domain.getCallSign().getCallSign();
    return format(
        "Link for Call Sign: %d and Driver %s active from %s",
        callSign, driverInfo, domain.getDateStart());
  }
}
