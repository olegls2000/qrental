package ee.qrental.balance.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrental.balance.api.in.query.GetBalanceCalculationQuery;
import ee.qrental.balance.api.in.response.BalanceCalculationResponse;
import ee.qrental.balance.api.out.BalanceCalculationLoadPort;
import ee.qrental.balance.core.mapper.BalanceCalculationResponseMapper;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationQueryService implements GetBalanceCalculationQuery {

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
}
