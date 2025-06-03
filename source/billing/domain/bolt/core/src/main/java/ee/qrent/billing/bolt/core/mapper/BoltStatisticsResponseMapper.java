package ee.qrent.billing.bolt.core.mapper;

import ee.qrent.billing.bolt.api.in.response.BoltStatisticsResponse;
import ee.qrent.billing.bolt.domain.BoltStatistics;
import ee.qrent.common.in.mapper.ResponseMapper;

import static java.lang.String.format;

public class BoltStatisticsResponseMapper
    implements ResponseMapper<BoltStatisticsResponse, BoltStatistics> {

  @Override
  public BoltStatisticsResponse toResponse(final BoltStatistics domain) {
    return BoltStatisticsResponse.builder().id(domain.getId()).build();
  }

  @Override
  public String toObjectInfo(final BoltStatistics domain) {
    return format("%s %s", domain.getFileName(), domain.getRegion());
  }
}
