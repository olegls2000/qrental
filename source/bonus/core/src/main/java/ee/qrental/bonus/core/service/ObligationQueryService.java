package ee.qrental.bonus.core.service;

import ee.qrental.bonus.api.in.query.GetObligationQuery;
import ee.qrental.bonus.api.in.response.ObligationResponse;
import ee.qrental.bonus.api.out.ObligationLoadPort;
import ee.qrental.bonus.core.mapper.ObligationResponseMapper;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObligationQueryService implements GetObligationQuery {

  private final GetQWeekQuery qWeekQuery;
  private final ObligationLoadPort loadPort;
  private final ObligationResponseMapper responseMapper;

  @Override
  public List<ObligationResponse> getAllByCalculationId(final Long calculationId) {
    return loadPort.loadAllByCalculationId(calculationId).stream()
        .map(responseMapper::toResponse)
        .toList();
  }

  @Override
  public ObligationResponse getForCurrentWeekByDriverId(final Long driverId) {
    final var currentQWeek = qWeekQuery.getCurrentWeek();
    if (currentQWeek == null) {
      throw new RuntimeException(
          "Q Week for the current day is missing. To get a latest Obligation is impossible. Please create Q Week");
    }
    final var obligation = loadPort.loadByDriverIdAndByQWeekId(driverId, currentQWeek.getId());

    return responseMapper.toResponse(obligation);
  }

  @Override
  public ObligationResponse getByQWeekIdAndDriverId(final Long qWeekId, final Long driverId) {
    final var obligation = loadPort.loadByDriverIdAndByQWeekId(driverId, qWeekId);

    return responseMapper.toResponse(obligation);
  }
}
