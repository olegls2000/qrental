package ee.qrent.billing.bolt.core.mapper;

import ee.qrent.billing.bolt.api.in.request.BoltStatisticsUpdateRequest;
import ee.qrent.billing.bolt.api.out.BoltStatisticsLoadPort;
import ee.qrent.billing.bolt.domain.BoltStatistics;
import ee.qrent.common.in.mapper.UpdateRequestMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoltStatisticsUpdateRequestMapper
    implements UpdateRequestMapper<BoltStatisticsUpdateRequest, BoltStatistics> {

  final BoltStatisticsLoadPort loadPort;

  @Override
  public BoltStatistics toDomain(final BoltStatisticsUpdateRequest request) {
    final var toBeUpdated = loadPort.loadById(request.getId());
    toBeUpdated.setYear(request.getYear());
    toBeUpdated.setMonth(request.getMonth());
    toBeUpdated.setRegion(request.getRegion());

    return toBeUpdated;
  }

  @Override
  public BoltStatisticsUpdateRequest toRequest(final BoltStatistics domain) {
    return BoltStatisticsUpdateRequest.builder()
        .id(domain.getId())
        .year(domain.getYear())
        .month(domain.getMonth())
        .region(domain.getRegion())
        .build();
  }
}
