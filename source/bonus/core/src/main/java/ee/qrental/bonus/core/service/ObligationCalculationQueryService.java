package ee.qrental.bonus.core.service;

import ee.qrental.bonus.api.in.response.ObligationCalculationResponse;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceCalculationQuery;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;


@AllArgsConstructor
public class ObligationCalculationQueryService implements GetObligationCalculationQuery {

  private final GetQWeekQuery qWeekQuery;
  private final GetBalanceCalculationQuery balanceCalculationQuery;
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
    final var lastRentCalculationQWeekId = loadPort.loadLastCalculationQWeekId();
    if (lastRentCalculationQWeekId != null) {

      return lastRentCalculationQWeekId;
    }
    final var lastBalanceCalculationQWeekId = balanceCalculationQuery.getLastCalculatedQWeekId();
    if (lastBalanceCalculationQWeekId != null) {

      return lastBalanceCalculationQWeekId;
    }

    return qWeekQuery.getFirstWeek().getId();
  }
}
