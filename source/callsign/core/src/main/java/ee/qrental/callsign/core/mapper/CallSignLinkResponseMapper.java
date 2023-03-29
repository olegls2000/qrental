package ee.qrental.callsign.core.mapper;

import static java.lang.String.format;

import ee.qrental.callsign.api.in.request.CallSignLinkResponse;
import ee.qrental.callsign.domain.CallSignLink;
import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignLinkResponseMapper
    implements ResponseMapper<CallSignLinkResponse, CallSignLink> {

  private final GetDriverQuery driverQuery;

  @Override
  public CallSignLinkResponse toResponse(final CallSignLink domain) {
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
    return format(
        "Call Sign Link for Driver %s. From %s till %s.",
        driverInfo, domain.getDateStart(), domain.getDateEnd());
  }
}
