package ee.qrent.billing.bolt.core.mapper;

import ee.qrent.billing.bolt.api.in.request.BoltStatisticsUpdateRequest;
import ee.qrent.billing.bolt.domain.BoltStatistics;
import ee.qrent.common.in.mapper.UpdateRequestMapper;

public class BoltStatisticsUpdateRequestMapper
    implements UpdateRequestMapper<BoltStatisticsUpdateRequest, BoltStatistics> {

  @Override
  public BoltStatistics toDomain(final BoltStatisticsUpdateRequest request) {
    return BoltStatistics.builder().id(request.getId()).build();
  }

  @Override
  public BoltStatisticsUpdateRequest toRequest(final BoltStatistics domain) {
    return BoltStatisticsUpdateRequest.builder().id(domain.getId()).build();
  }
}
