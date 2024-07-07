package ee.qrental.transaction.core.service.balance;

import static ee.qrental.common.utils.QTimeUtils.getWeekNumber;
import static java.util.stream.Collectors.toList;


import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceCalculationQuery;
import ee.qrental.transaction.api.in.response.balance.BalanceCalculationResponse;
import ee.qrental.transaction.api.out.balance.BalanceCalculationLoadPort;
import ee.qrental.transaction.core.mapper.balance.BalanceCalculationResponseMapper;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationQueryService implements GetBalanceCalculationQuery {

  private final GetQWeekQuery qWeekQuery;
  private final BalanceCalculationLoadPort loadPort;
  private final BalanceCalculationResponseMapper responseMapper;

  @Override
  public List<BalanceCalculationResponse> getAll() {
    return loadPort.loadAll().stream().map(responseMapper::toResponse).collect(toList());
  }

  @Override
  public BalanceCalculationResponse getById(final Long id) {
    return responseMapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public Long getLastCalculatedQWeekId() {
    final var lastCalculatedDate = loadPort.loadLastCalculatedDate();
    if (lastCalculatedDate == null) {

      return null;
    }
    final var year = lastCalculatedDate.getYear();
    final var weekNumber = getWeekNumber(lastCalculatedDate);
    final var lastCalculatedWeek = qWeekQuery.getByYearAndNumber(year, weekNumber);

    return lastCalculatedWeek.getId();
  }
}
