package ee.qrental.callsign.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrental.callsign.api.in.query.GetCallSignLinkQuery;
import ee.qrental.callsign.api.in.request.CallSignLinkResponse;
import ee.qrental.callsign.api.in.request.CallSignLinkUpdateRequest;
import ee.qrental.callsign.api.out.CallSignLinkLoadPort;
import ee.qrental.callsign.core.mapper.CallSignLinkResponseMapper;
import ee.qrental.callsign.core.mapper.CallSignLinkUpdateRequestMapper;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignLinkQueryService implements GetCallSignLinkQuery {

  private final CallSignLinkLoadPort loadPort;
  private final CallSignLinkResponseMapper mapper;
  private final CallSignLinkUpdateRequestMapper updateRequestMapper;

  @Override
  public List<CallSignLinkResponse> getAll() {
    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public CallSignLinkResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public CallSignLinkUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }

  @Override
  public CallSignLinkResponse getActiveCallSignLinkByDriverId(final Long driverId) {
    return mapper.toResponse(loadPort.loadActiveByDriverId(driverId));
  }
}
