package ee.qrental.driver.core.mapper;

import static java.lang.String.format;

import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.request.FirmLinkResponse;
import ee.qrental.driver.domain.FirmLink;
import ee.qrental.firm.api.in.query.GetFirmQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FirmLinkResponseMapper
    implements ResponseMapper<FirmLinkResponse, FirmLink> {

  private final GetDriverQuery driverQuery;
  private final GetFirmQuery firmQuery;

  @Override
  public FirmLinkResponse toResponse(final FirmLink domain) {
    if (domain == null) {
      return null;
    }

final var firmId = domain.getFirmId();
    final var firmInfo = firmQuery.getObjectInfo(firmId);

    return FirmLinkResponse.builder()
        .id(domain.getId())
        .firmId(firmId)
        .firmInfo(firmInfo)
        .driverId(domain.getDriverId())
        .dateStart(domain.getDateStart())
        .dateEnd(domain.getDateEnd())
        .driverInfo(driverQuery.getObjectInfo(domain.getDriverId()))
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(final FirmLink domain) {
    final var driverInfo = driverQuery.getObjectInfo(domain.getDriverId());
    return format(
        "Firm Link for Driver %s. From %s till %s.",
        driverInfo, domain.getDateStart(), domain.getDateEnd());
  }
}
