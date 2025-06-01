package ee.qrent.billing.deposit.core.service;

import ee.qrent.billing.deposit.api.in.query.GetDepositQuery;
import ee.qrent.billing.deposit.api.in.request.DepositUpdateRequest;
import ee.qrent.billing.deposit.api.in.response.DepositResponse;
import ee.qrent.billing.deposit.api.out.DepositLoadPort;
import ee.qrent.billing.deposit.core.mapper.DepositResponseMapper;
import ee.qrent.billing.deposit.core.mapper.DepositUpdateRequestMapper;
import ee.qrent.billing.deposit.domain.Deposit;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class DepositQueryService implements GetDepositQuery {

  private final DepositLoadPort loadPort;
  private final DepositResponseMapper mapper;
  private final DepositUpdateRequestMapper updateRequestMapper;

  @Override
  public List<DepositResponse> getAll() {

    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public DepositResponse getById(final Long id) {

    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {

    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public DepositUpdateRequest getUpdateRequestById(Long id) {

    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }

  @Override
  public BigDecimal getPaidAmountByDriverId(final Long driverId) {

    return loadPort.loadAllByDriverId(driverId).stream()
        .map(Deposit::getAmount)
        .reduce(ZERO, BigDecimal::add);
  }
}
