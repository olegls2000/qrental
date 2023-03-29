package ee.qrental.callsign.core.mapper;

import ee.qrental.callsign.api.in.request.CallSignLinkAddRequest;
import ee.qrental.callsign.api.out.CallSignLoadPort;
import ee.qrental.callsign.domain.CallSignLink;
import ee.qrental.common.core.in.mapper.AddRequestMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignLinkAddRequestMapper
    implements AddRequestMapper<CallSignLinkAddRequest, CallSignLink> {

  private final CallSignLoadPort callSignLoadPort;

  @Override
  public CallSignLink toDomain(CallSignLinkAddRequest request) {
    return CallSignLink.builder()
        .id(null)
        .driverId(request.getDriverId())
        .callSign(callSignLoadPort.loadById(request.getCallSignId()))
        .dateStart(request.getDateStart())
        .dateEnd(request.getDateEnd())
        .comment(request.getComment())
        .build();
  }
}
