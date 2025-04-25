package ee.qrent.billing.driver.core.mapper;

import ee.qrent.common.in.mapper.UpdateRequestMapper;
import ee.qrent.billing.driver.api.in.request.FirmLinkUpdateRequest;
import ee.qrent.billing.driver.domain.FirmLink;
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
