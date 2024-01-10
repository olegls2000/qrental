package ee.qrental.bonus.core.mapper;

import static java.lang.String.format;

import ee.qrental.bonus.api.in.response.WeekObligationResponse;
import ee.qrental.bonus.domain.WeekObligation;
import ee.qrental.common.core.in.mapper.ResponseMapper;

public class WeekObligationResponseMapper
    implements ResponseMapper<WeekObligationResponse, WeekObligation> {
  @Override
  public WeekObligationResponse toResponse(final WeekObligation domain) {
    return WeekObligationResponse.builder().id(domain.getId()).build();
  }


  @Override
  public String toObjectInfo(WeekObligation domain) {
    return format("%d %d", domain.getDriverId(), domain.getMatchCount());
  }
}
