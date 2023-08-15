package ee.qrental.driver.core.mapper;

import ee.qrental.common.core.in.mapper.UpdateRequestMapper;
import ee.qrental.driver.api.in.request.CallSignLinkUpdateRequest;
import ee.qrental.driver.api.in.request.FirmLinkUpdateRequest;
import ee.qrental.driver.api.out.CallSignLoadPort;
import ee.qrental.driver.domain.CallSignLink;
import ee.qrental.driver.domain.FirmLink;
import ee.qrental.firm.api.in.query.GetFirmQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FirmLinkUpdateRequestMapper
    implements UpdateRequestMapper<FirmLinkUpdateRequest, FirmLink> {

  @Override
  public FirmLink toDomain(final FirmLinkUpdateRequest request) {
    return FirmLink.builder()
        .id(request.getId())
        .driverId(request.getDriverId())
        .firmId(request.getFirmId())
        .dateStart(request.getDateStart())
        .dateEnd(request.getDateEnd())
        .comment(request.getComment())
        .build();
  }

  @Override
  public FirmLinkUpdateRequest toRequest(final FirmLink domain) {
    final var request = new FirmLinkUpdateRequest();
    request.setId(domain.getId());
    request.setFirmId(domain.getFirmId());
    request.setDriverId(domain.getDriverId());
    request.setDateStart(domain.getDateStart());
    request.setDateEnd(domain.getDateEnd());
    request.setComment(domain.getComment());

    return request;
  }
}
