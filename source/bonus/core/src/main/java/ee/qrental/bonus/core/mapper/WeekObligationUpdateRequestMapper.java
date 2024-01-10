package ee.qrental.bonus.core.mapper;

import ee.qrental.bonus.api.in.request.WeekObligationUpdateRequest;
import ee.qrental.bonus.domain.WeekObligation;
import ee.qrental.common.core.in.mapper.UpdateRequestMapper;

public class WeekObligationUpdateRequestMapper
    implements UpdateRequestMapper<WeekObligationUpdateRequest, WeekObligation> {

  @Override
  public WeekObligation toDomain(final WeekObligationUpdateRequest request) {
    return WeekObligation.builder().id(request.getId()).build();
  }

  @Override
  public WeekObligationUpdateRequest toRequest(final WeekObligation domain) {
    return WeekObligationUpdateRequest.builder().id(domain.getId()).build();
  }
}
