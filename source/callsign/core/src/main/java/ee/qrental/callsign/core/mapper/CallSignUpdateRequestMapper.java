package ee.qrental.callsign.core.mapper;

import ee.qrental.callsign.api.in.request.CallSignUpdateRequest;
import ee.qrental.callsign.domain.CallSign;
import ee.qrental.common.core.in.mapper.UpdateRequestMapper;

public class CallSignUpdateRequestMapper
    implements UpdateRequestMapper<CallSignUpdateRequest, CallSign> {

  @Override
  public CallSign toDomain(final CallSignUpdateRequest request) {
    return CallSign.builder()
        .id(request.getId())
        .callSign(request.getCallSign())
        .comment(request.getComment())
        .build();
  }

  @Override
  public CallSignUpdateRequest toRequest(final CallSign domain) {
    return CallSignUpdateRequest.builder()
        .id(domain.getId())
        .callSign(domain.getCallSign())
        .comment(domain.getComment())
        .build();
  }
}
