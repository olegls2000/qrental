package ee.qrental.callsign.core.mapper;

import static java.lang.String.format;

import ee.qrental.callsign.api.in.response.CallSignResponse;
import ee.qrental.callsign.domain.CallSign;
import ee.qrental.common.core.in.mapper.ResponseMapper;

public class CallSignResponseMapper implements ResponseMapper<CallSignResponse, CallSign> {
  @Override
  public CallSignResponse toResponse(final CallSign domain) {
    return CallSignResponse.builder()
        .id(domain.getId())
        .callSign(domain.getCallSign())
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(CallSign domain) {
    return format("%s", domain.getCallSign());
  }
}
