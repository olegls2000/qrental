package ee.qrent.billing.bonus.core.service;

import ee.qrent.billing.bonus.api.in.query.GetObligationQuery;
import ee.qrent.billing.bonus.api.in.response.ObligationResponse;
import ee.qrent.billing.bonus.api.out.ObligationLoadPort;
import ee.qrent.billing.bonus.core.mapper.ObligationResponseMapper;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObligationQueryService implements GetObligationQuery {

  private final GetQWeekQuery qWeekQuery;
  private final ObligationLoadPort loadPort;
  private final ObligationCalculator obligationCalculator;
  private final ObligationResponseMapper responseMapper;

  @Override
  public List<ObligationResponse> getAllByCalculationId(final Long calculationId) {
    return loadPort.loadAllByCalculationId(calculationId).stream()
        .map(responseMapper::toResponse)
        .toList();
  }

  @Override
  public BigDecimal getRawObligationAmountForCurrentWeekByDriverId(final Long driverId) {
    final var currentQWeek = qWeekQuery.getCurrentWeek();
    final var rawObligationAmount = obligationCalculator.calculate(driverId, currentQWeek.getId());

    return rawObligationAmount;
  }

  @Override
  public ObligationResponse getObligationAmountForPreCurrentWeekByDriverId(final Long driverId) {
    final var currentQWeek = qWeekQuery.getCurrentWeek();
    final var preCurrentWeek = qWeekQuery.getOneBeforeById(currentQWeek.getId());

    return getByQWeekIdAndDriverId(preCurrentWeek.getId(), driverId);
  }

  @Override
  public ObligationResponse getByQWeekIdAndDriverId(final Long qWeekId, final Long driverId) {
    final var obligation = loadPort.loadByDriverIdAndByQWeekId(driverId, qWeekId);

    return responseMapper.toResponse(obligation);
  }
}
