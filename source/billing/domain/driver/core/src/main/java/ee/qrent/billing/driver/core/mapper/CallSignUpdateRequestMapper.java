package ee.qrent.billing.driver.core.mapper;

import ee.qrent.common.in.mapper.UpdateRequestMapper;
import ee.qrent.billing.driver.api.in.request.CallSignUpdateRequest;
import ee.qrent.billing.driver.domain.CallSign;

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
