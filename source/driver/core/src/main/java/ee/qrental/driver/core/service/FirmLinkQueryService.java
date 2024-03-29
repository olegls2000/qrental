package ee.qrental.driver.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrental.driver.api.in.query.GetFirmLinkQuery;
import ee.qrental.driver.api.in.request.FirmLinkResponse;
import ee.qrental.driver.api.in.request.FirmLinkUpdateRequest;
import ee.qrental.driver.api.out.FirmLinkLoadPort;
import ee.qrental.driver.core.mapper.FirmLinkResponseMapper;
import ee.qrental.driver.core.mapper.FirmLinkUpdateRequestMapper;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FirmLinkQueryService implements GetFirmLinkQuery {

  private final FirmLinkLoadPort loadPort;
  private final FirmLinkResponseMapper mapper;
  private final FirmLinkUpdateRequestMapper updateRequestMapper;

  @Override
  public List<FirmLinkResponse> getAll() {
    return loadPort.loadAll().stream()
        .map(mapper::toResponse)
        .collect(toList());
  }

  @Override
  public FirmLinkResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public FirmLinkUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }

  @Override
  public FirmLinkResponse getOneByDriverIdAndRequiredDate(
          final Long driverId, final LocalDate requiredDate) {
    return mapper.toResponse(loadPort.loadOneByDriverIdAndRequiredDate(driverId, requiredDate));
  }
}