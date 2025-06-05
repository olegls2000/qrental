package ee.qrent.billing.bolt.core.mapper;

import ee.qrent.billing.bolt.api.in.request.BoltStatisticsAddRequest;
import ee.qrent.billing.bolt.domain.BoltStatistics;
import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrent.common.in.time.QDateTime;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoltStatisticsAddRequestMapper
    implements AddRequestMapper<BoltStatisticsAddRequest, BoltStatistics> {

  private final QDateTime qDateTime;

  @Override
  public BoltStatistics toDomain(final BoltStatisticsAddRequest request) {
    return BoltStatistics.builder()
        .id(null)
        .createdOn(qDateTime.getToday())
        .region(request.getRegion())
        .inputStream(request.getInputStream())
        .year(request.getYear())
        .month(request.getMonth())
        .fileName(request.getFileName())
        .build();
  }
}
