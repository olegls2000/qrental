package ee.qrental.firm.core.service;

import ee.qrental.firm.api.in.query.GetFirmQuery;
import ee.qrental.firm.api.in.request.FirmUpdateRequest;
import ee.qrental.firm.api.in.response.FirmResponse;
import ee.qrental.firm.api.out.FirmLoadPort;
import ee.qrental.firm.core.mapper.FirmResponseMapper;
import ee.qrental.firm.core.mapper.FirmUpdateRequestMapper;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;



@AllArgsConstructor
public class FirmQueryService implements GetFirmQuery {

  private final FirmLoadPort loadPort;
  private final FirmResponseMapper mapper;
  private final FirmUpdateRequestMapper updateRequestMapper;

  @Override
  public List<FirmResponse> getAll() {
    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public FirmResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public FirmUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }
}
