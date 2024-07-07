package ee.qrental.driver.core.mapper;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrental.driver.api.in.request.FirmLinkAddRequest;
import ee.qrental.driver.domain.FirmLink;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FirmLinkAddRequestMapper
    implements AddRequestMapper<FirmLinkAddRequest, FirmLink> {

  @Override
  public FirmLink toDomain(final FirmLinkAddRequest request) {
    return FirmLink.builder()
        .id(null)
        .driverId(request.getDriverId())
        .firmId(request.getFirmId())
        .dateStart(request.getDateStart())
        .dateEnd(request.getDateEnd())
        .comment(request.getComment())
        .build();
  }
}
