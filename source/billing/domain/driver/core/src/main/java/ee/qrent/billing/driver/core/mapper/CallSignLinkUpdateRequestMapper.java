package ee.qrent.billing.driver.core.mapper;

import ee.qrent.common.in.mapper.UpdateRequestMapper;
import ee.qrent.billing.driver.api.in.request.CallSignLinkUpdateRequest;
import ee.qrent.billing.driver.api.out.CallSignLoadPort;
import ee.qrent.billing.driver.domain.CallSignLink;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignLinkUpdateRequestMapper
    implements UpdateRequestMapper<CallSignLinkUpdateRequest, CallSignLink> {

  private final CallSignLoadPort callSignLoadPort;

  @Override
  public CallSignLink toDomain(final CallSignLinkUpdateRequest request) {
    return CallSignLink.builder()
        .id(request.getId())
        .driverId(request.getDriverId())
        .callSign(callSignLoadPort.loadById(request.getCallSignId()))
        .dateStart(request.getDateStart())
        .dateEnd(request.getDateEnd())
        .comment(request.getComment())
        .build();
  }

  @Override
  public CallSignLinkUpdateRequest toRequest(final CallSignLink domain) {
    final var request = new CallSignLinkUpdateRequest();
    request.setId(domain.getId());
    request.setCallSignId(domain.getCallSign().getId());
    request.setDriverId(domain.getDriverId());
    request.setDateStart(domain.getDateStart());
    request.setDateEnd(domain.getDateEnd());
    request.setComment(domain.getComment());

    return request;
  }
}
