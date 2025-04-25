package ee.qrent.billing.bonus.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.bonus.api.in.query.GetBonusCalculationQuery;
import ee.qrent.billing.bonus.api.in.response.BonusCalculationResponse;
import ee.qrent.billing.bonus.api.out.BonusCalculationLoadPort;
import ee.qrent.billing.bonus.core.mapper.BonusCalculationResponseMapper;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BonusCalculationQueryService implements GetBonusCalculationQuery {

  private final GetQWeekQuery qWeekQuery;
  private final BonusCalculationLoadPort loadPort;
  private final BonusCalculationResponseMapper responseMapper;

  @Override
  public List<BonusCalculationResponse> getAll() {
    return loadPort.loadAll().stream().map(responseMapper::toResponse).collect(toList());
  }

  @Override
  public BonusCalculationResponse getById(final Long id) {
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
