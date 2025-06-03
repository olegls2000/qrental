package ee.qrent.billing.bolt.core.service;

import ee.qrent.billing.bolt.api.in.request.BoltStatisticsAddRequest;
import ee.qrent.billing.bolt.api.in.request.BoltStatisticsDeleteRequest;
import ee.qrent.billing.bolt.api.in.request.BoltStatisticsUpdateRequest;
import ee.qrent.billing.bolt.api.in.usecase.BoltStatisticsAddUseCase;
import ee.qrent.billing.bolt.api.in.usecase.BoltStatisticsDeleteUseCase;
import ee.qrent.billing.bolt.api.in.usecase.BoltStatisticsUpdateUseCase;
import ee.qrent.billing.bolt.api.out.BoltStatisticsAddPort;
import ee.qrent.billing.bolt.api.out.BoltStatisticsDeletePort;
import ee.qrent.billing.bolt.api.out.BoltStatisticsLoadPort;
import ee.qrent.billing.bolt.api.out.BoltStatisticsUpdatePort;
import ee.qrent.billing.bolt.core.mapper.BoltStatisticsAddRequestMapper;
import ee.qrent.billing.bolt.core.mapper.BoltStatisticsUpdateRequestMapper;
import ee.qrent.billing.bolt.core.validator.BoltStatisticsRequestValidator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoltStatisticsUseCaseService
    implements BoltStatisticsAddUseCase, BoltStatisticsUpdateUseCase, BoltStatisticsDeleteUseCase {

  private final BoltStatisticsAddPort addPort;
  private final BoltStatisticsUpdatePort updatePort;
  private final BoltStatisticsDeletePort deletePort;
  private final BoltStatisticsLoadPort loadPort;
  private final BoltStatisticsAddRequestMapper addRequestMapper;
  private final BoltStatisticsUpdateRequestMapper updateRequestMapper;
  private final BoltStatisticsRequestValidator requestValidator;

  @Override
  public Long add(final BoltStatisticsAddRequest request) {
    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }

  @Override
  public void update(final BoltStatisticsUpdateRequest request) {
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Override
  public void delete(final BoltStatisticsDeleteRequest request) {
    deletePort.delete(request.getId());
  }
}
