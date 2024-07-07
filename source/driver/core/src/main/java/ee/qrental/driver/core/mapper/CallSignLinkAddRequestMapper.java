package ee.qrental.driver.core.mapper;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrental.driver.api.in.request.CallSignLinkAddRequest;
import ee.qrental.driver.api.out.CallSignLoadPort;
import ee.qrental.driver.domain.CallSignLink;
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
