package ee.qrental.bonus.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrental.bonus.api.in.query.GetBonusCalculationQuery;
import ee.qrental.bonus.api.in.response.BonusCalculationResponse;
import ee.qrental.bonus.api.out.BonusCalculationLoadPort;
import ee.qrental.bonus.core.mapper.BonusCalculationResponseMapper;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
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
