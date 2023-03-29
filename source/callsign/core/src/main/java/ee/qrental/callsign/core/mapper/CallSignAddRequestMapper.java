package ee.qrental.callsign.core.mapper;

import ee.qrental.callsign.api.in.request.CallSignAddRequest;
import ee.qrental.callsign.domain.CallSign;
import ee.qrental.common.core.in.mapper.AddRequestMapper;

public class CallSignAddRequestMapper implements AddRequestMapper<CallSignAddRequest, CallSign> {

  @Override
  public CallSign toDomain(CallSignAddRequest request) {
    return CallSign.builder()
        .id(null)
        .callSign(request.getCallSign())
        .comment(request.getComment())
        .build();
  }
}
