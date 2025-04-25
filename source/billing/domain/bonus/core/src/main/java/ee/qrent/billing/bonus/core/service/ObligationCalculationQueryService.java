package ee.qrent.billing.bonus.core.service;

import ee.qrent.billing.bonus.api.in.query.GetObligationCalculationQuery;
import ee.qrent.billing.bonus.api.in.response.ObligationCalculationResponse;
import ee.qrent.billing.bonus.api.out.ObligationCalculationLoadPort;
import ee.qrent.billing.bonus.core.mapper.ObligationCalculationResponseMapper;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class ObligationCalculationQueryService implements GetObligationCalculationQuery {

  private final GetQWeekQuery qWeekQuery;
  private final ObligationCalculationLoadPort loadPort;
  private final ObligationCalculationResponseMapper responseMapper;

  @Override
  public List<ObligationCalculationResponse> getAll() {
    return loadPort.loadAll().stream().map(responseMapper::toResponse).collect(toList());
  }

  @Override
  public ObligationCalculationResponse getById(final Long id) {
    return responseMapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public Long getLastCalculatedQWeekId() {
    final var lastCalculationQWeekId = loadPort.loadLastCalculatedQWeekId();
    if (lastCalculationQWeekId != null) {

      return lastCalculationQWeekId;
    }

    return qWeekQuery.getFirstWeek().getId();
  }
}
