package ee.qrent.billing.bolt.core.service;

import ee.qrent.billing.bolt.api.in.query.GetBoltStatisticsQuery;
import ee.qrent.billing.bolt.api.in.request.BoltStatisticsUpdateRequest;
import ee.qrent.billing.bolt.api.in.response.BoltStatisticsResponse;
import ee.qrent.billing.bolt.api.out.BoltStatisticsLoadPort;
import ee.qrent.billing.bolt.core.mapper.BoltStatisticsResponseMapper;
import ee.qrent.billing.bolt.core.mapper.BoltStatisticsUpdateRequestMapper;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class BoltStatisticsQueryService implements GetBoltStatisticsQuery {

  private final BoltStatisticsLoadPort loadPort;
  private final BoltStatisticsResponseMapper mapper;
  private final BoltStatisticsUpdateRequestMapper updateRequestMapper;

  @Override
  public List<BoltStatisticsResponse> getAll() {
    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public BoltStatisticsResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public BoltStatisticsUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }
}
