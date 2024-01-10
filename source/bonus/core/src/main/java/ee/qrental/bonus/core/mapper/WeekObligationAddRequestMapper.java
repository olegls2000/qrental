package ee.qrental.bonus.core.mapper;

import ee.qrental.bonus.domain.WeekObligation;
import ee.qrental.common.core.in.mapper.AddRequestMapper;
import ee.qrental.bonus.api.in.request.WeekObligationAddRequest;

public class WeekObligationAddRequestMapper
    implements AddRequestMapper<WeekObligationAddRequest, WeekObligation> {

  @Override
  public WeekObligation toDomain(WeekObligationAddRequest request) {
    return WeekObligation.builder().id(null).build();
  }
}
