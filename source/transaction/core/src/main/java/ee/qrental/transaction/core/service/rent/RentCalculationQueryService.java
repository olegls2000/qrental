package ee.qrental.transaction.core.service.rent;

import static java.util.stream.Collectors.toList;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceCalculationQuery;
import ee.qrental.transaction.api.in.query.rent.GetRentCalculationQuery;
import ee.qrental.transaction.api.in.response.rent.RentCalculationResponse;
import ee.qrental.transaction.api.out.rent.RentCalculationLoadPort;
import ee.qrental.transaction.core.mapper.rent.RentCalculationResponseMapper;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RentCalculationQueryService implements GetRentCalculationQuery {

  private final GetQWeekQuery qWeekQuery;
  private final GetBalanceCalculationQuery balanceCalculationQuery;
  private final RentCalculationLoadPort loadPort;
  private final RentCalculationResponseMapper responseMapper;

  @Override
  public List<RentCalculationResponse> getAll() {
    return loadPort.loadAll().stream().map(responseMapper::toResponse).collect(toList());
  }

  @Override
  public RentCalculationResponse getById(final Long id) {
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
