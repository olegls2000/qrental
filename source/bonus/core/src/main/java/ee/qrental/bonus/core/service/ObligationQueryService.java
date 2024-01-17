package ee.qrental.bonus.core.service;


import ee.qrental.bonus.api.in.query.GetObligationQuery;
import ee.qrental.bonus.api.in.response.ObligationResponse;
import ee.qrental.bonus.api.out.ObligationLoadPort;
import ee.qrental.bonus.core.mapper.ObligationResponseMapper;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObligationQueryService implements GetObligationQuery {

  private final ObligationLoadPort loadPort;
  private final ObligationResponseMapper responseMapper;

  @Override
  public List<ObligationResponse> getAllByCalculationId(final Long calculationId) {
    return loadPort.loadAllByCalculationId(calculationId).stream().map(responseMapper::toResponse).toList();
  }
}
