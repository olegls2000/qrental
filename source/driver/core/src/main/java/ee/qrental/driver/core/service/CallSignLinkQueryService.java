package ee.qrental.driver.core.service;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

import ee.qrental.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrental.driver.api.in.request.CallSignLinkResponse;
import ee.qrental.driver.api.in.request.CallSignLinkUpdateRequest;
import ee.qrental.driver.api.out.CallSignLinkLoadPort;
import ee.qrental.driver.core.mapper.CallSignLinkResponseMapper;
import ee.qrental.driver.core.mapper.CallSignLinkUpdateRequestMapper;
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
  public CallSignLinkUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }

  @Override
  public CallSignLinkResponse getActiveCallSignLinkByDriverId(final Long driverId) {
    return mapper.toResponse(loadPort.loadActiveByDriverId(driverId));
  }

  @Override
  public List<CallSignLinkResponse> getCallSignLinksByDriverId(final Long driverId) {
    return loadPort.loadByDriverId(driverId).stream()
        .map(mapper::toResponse)
        .sorted(DEFAULT_COMPARATOR)
        .collect(toList());
  }

  @Override
  public CallSignLinkResponse getCallSignLinkByDriverIdAndDate(
      final Long driverId, final LocalDate date) {
    return mapper.toResponse(loadPort.loadByDriverIdAndDate(driverId, date));
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
