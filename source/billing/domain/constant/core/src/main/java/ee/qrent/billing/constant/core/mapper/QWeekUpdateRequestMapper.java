package ee.qrent.billing.constant.core.mapper;

import ee.qrent.common.in.mapper.UpdateRequestMapper;
import ee.qrent.billing.constant.api.in.request.QWeekUpdateRequest;
import ee.qrent.billing.constant.domain.QWeek;

public class QWeekUpdateRequestMapper implements UpdateRequestMapper<QWeekUpdateRequest, QWeek> {

  @Override
  public QWeek toDomain(final QWeekUpdateRequest request) {
    return QWeek.builder().id(request.getId()).description(request.getDescription()).build();
  }

  @Override
  public QWeekUpdateRequest toRequest(final QWeek domain) {
    return QWeekUpdateRequest.builder()
        .id(domain.getId())
        .description(domain.getDescription())
        .build();
  }
}
