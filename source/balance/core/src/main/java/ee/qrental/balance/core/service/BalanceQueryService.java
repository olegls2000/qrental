package ee.qrental.balance.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrental.balance.api.in.query.GetBalanceQuery;
import ee.qrental.balance.api.in.response.BalanceResponse;
import ee.qrental.balance.api.out.BalanceLoadPort;
import ee.qrental.balance.core.mapper.BalanceResponseMapper;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceQueryService implements GetBalanceQuery {

  private final BalanceLoadPort loadPort;
  private final BalanceResponseMapper mapper;

  @Override
  public List<BalanceResponse> getAll() {
    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public BalanceResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public BalanceResponse getByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber) {
    if (year == 2023 && weekNumber < 10) {
      return BalanceResponse.builder().amount(BigDecimal.ZERO).build();
    }

    return mapper.toResponse(
        loadPort.loadByDriverIdAndYearAndWeekNumber(driverId, year, weekNumber));
  }
}
