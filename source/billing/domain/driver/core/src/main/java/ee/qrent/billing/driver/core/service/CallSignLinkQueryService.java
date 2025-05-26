package ee.qrent.billing.driver.core.service;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrent.billing.driver.api.in.request.CallSignLinkResponse;
import ee.qrent.billing.driver.api.in.request.CallSignLinkUpdateRequest;
import ee.qrent.billing.driver.api.out.CallSignLinkLoadPort;
import ee.qrent.billing.driver.core.mapper.CallSignLinkResponseMapper;
import ee.qrent.billing.driver.core.mapper.CallSignLinkUpdateRequestMapper;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignLinkQueryService implements GetCallSignLinkQuery {

  private final Comparator<CallSignLinkResponse> DEFAULT_COMPARATOR =
      comparingInt(CallSignLinkResponse::getCallSign);
  private final Comparator<CallSignLinkResponse> DATE_COMPARATOR =
      comparing(CallSignLinkResponse::getDateEnd).reversed();

  private final CallSignLinkLoadPort loadPort;
  private final CallSignLinkResponseMapper mapper;
  private final CallSignLinkUpdateRequestMapper updateRequestMapper;
  private final GetQWeekQuery qWeekQuery;

  @Override
  public List<CallSignLinkResponse> getAll() {
    return loadPort.loadAll().stream()
        .map(mapper::toResponse)
        .sorted(getCallSignOrEndDateComparator())
        .collect(toList());
  }

  @Override
  public CallSignLinkResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public CallSignLinkUpdateRequest getUpdateRequestById(final Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }

  @Override
  public CallSignLinkResponse getActiveCallSignLinkByDriverId(final Long driverId) {
    return mapper.toResponse(loadPort.loadActiveByDriverId(driverId));
  }

  @Override
  public CallSignLinkResponse getActiveByDriverIdAndQWeekId(
      final Long driverId, final Long qWeekId) {
    final var qWeek = qWeekQuery.getById(qWeekId);

    return mapper.toResponse(loadPort.loadByDriverIdAndDate(driverId, qWeek.getStart()));
  }

  @Override
  public List<CallSignLinkResponse> getActive() {
    return loadPort.loadActiveByDate(LocalDate.now()).stream()
        .map(mapper::toResponse)
        .sorted(DEFAULT_COMPARATOR)
        .collect(toList());
  }

  @Override
  public List<CallSignLinkResponse> getClosed() {
    return loadPort.loadClosedByDate(LocalDate.now()).stream()
        .map(mapper::toResponse)
        .sorted(DEFAULT_COMPARATOR.thenComparing(DATE_COMPARATOR))
        .collect(toList());
  }

  @Override
  public Long getCountActive() {
    return loadPort.loadCountActiveByDate(LocalDate.now());
  }

  @Override
  public Long getCountClosed() {
    return loadPort.loadCountClosedByDate(LocalDate.now());
  }

  private Comparator<CallSignLinkResponse> getCallSignOrEndDateComparator() {
    return (callSignLink1, callSignLink2) -> {
      final var callSign1 = callSignLink1.getCallSign();
      final var callSign2 = callSignLink2.getCallSign();

      return callSign1.compareTo(callSign2);
    };
  }
}
