package ee.qrental.constant.core.mapper;

import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.constant.domain.QWeek;

public class QWeekResponseMapper implements ResponseMapper<QWeekResponse, QWeek> {
  @Override
  public QWeekResponse toResponse(final QWeek domain) {
    return QWeekResponse.builder()
        .id(domain.getId())
        .year(domain.getYear())
        .number(domain.getNumber())
        .start(domain.getStart())
        .end(domain.getEnd())
        .description(domain.getDescription())
        .build();
  }

  @Override
  public String toObjectInfo(QWeek domain) {
    return domain.getDescription();
  }
}
