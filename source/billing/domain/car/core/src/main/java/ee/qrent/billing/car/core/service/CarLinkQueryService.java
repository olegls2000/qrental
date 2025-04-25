package ee.qrent.billing.car.core.service;

import static java.lang.String.format;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.car.api.in.request.CarLinkUpdateRequest;
import ee.qrent.billing.car.api.in.response.CarLinkResponse;
import ee.qrent.billing.car.api.out.CarLinkLoadPort;
import ee.qrent.billing.car.core.mapper.CarLinkResponseMapper;
import ee.qrent.billing.car.core.mapper.CarLinkUpdateRequestMapper;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CarLinkQueryService implements GetCarLinkQuery {
  private final Comparator<CarLinkResponse> DEFAULT_COMPARATOR =
      (o1, o2) -> {
        final var callSign1 = o1.getCallSign();
        final var callSign2 = o2.getCallSign();

        if (callSign1 == null && callSign2 == null) {

          return 0;
        }
        if (callSign1 != null && callSign2 == null) {

          return -1;
        }

        if (callSign1 == null && callSign2 != null) {

          return 1;
        }

        return callSign1 - callSign2;
      };

  private final Comparator<CarLinkResponse> DRIVER_COMPARATOR =
      comparing(CarLinkResponse::getDriverInfo);
  private final Comparator<CarLinkResponse> END_DATE_COMPARATOR =
      comparing(CarLinkResponse::getDateEnd);

  private final CarLinkLoadPort loadPort;
  private final CarLinkResponseMapper mapper;
  private final CarLinkUpdateRequestMapper updateRequestMapper;
  private final QDateTime qDateTime;
  private final GetQWeekQuery qWeekQuery;

  @Override
  public List<CarLinkResponse> getAll() {
    return loadPort.loadAll().stream()
        .map(mapper::toResponse)
        .sorted(DEFAULT_COMPARATOR)
        .collect(toList());
  }

  @Override
  public CarLinkResponse getById(final Long id) {
    final var domain = loadPort.loadById(id);
    if (domain == null) {
      System.out.println(format("Link with id = %d was not found", id));
      return null;
    }
    return mapper.toResponse(domain);
  }

  @Override
  public String getObjectInfo(final Long id) {
    final var domain = loadPort.loadById(id);
    if (domain == null) {
      System.out.println(format("Link with id = %d was not found", id));
      return null;
    }
    return mapper.toObjectInfo(domain);
  }

  @Override
  public CarLinkUpdateRequest getUpdateRequestById(Long id) {
    final var domain = loadPort.loadById(id);
    if (domain == null) {
      System.out.println(format("Link with id = %d was not found", id));
      return null;
    }
    return updateRequestMapper.toRequest(domain);
  }

  @Override
  public CarLinkResponse getActiveLinkByDriverId(final Long driverId) {
    final var domain = loadPort.loadActiveByDriverId(driverId);
    if (domain == null) {
      System.out.println(format("Active Link for driver with id = %d was not found", driverId));
      return null;
    }
    return mapper.toResponse(domain);
  }

  @Override
  public CarLinkResponse getFirstLinkByDriverId(final Long driverId) {
    final var domain = loadPort.loadActiveByDriverId(driverId);
    if (domain == null) {
      System.out.println(format("Driver with id = %d does not have Cal Link", driverId));
      return null;
    }
    return mapper.toResponse(domain);
  }

  @Override
  public List<CarLinkResponse> getAllActiveForCurrentDate() {

    return loadPort.loadActiveByDate(qDateTime.getToday()).stream()
        .map(mapper::toResponse)
        .sorted(DEFAULT_COMPARATOR)
        .collect(toList());
  }

  @Override
  public List<CarLinkResponse> getAllActiveByQWeekId(final Long weekId) {
    final var qWeek = qWeekQuery.getById(weekId);

    return loadPort.loadActiveByDate(qWeek.getStart()).stream()
        .map(mapper::toResponse)
        .sorted(DEFAULT_COMPARATOR)
        .collect(toList());
  }

  @Override
  public Long getCountActiveForCurrentDate() {
    return loadPort.loadCountActiveByDate(qDateTime.getToday());
  }

  @Override
  public List<CarLinkResponse> getClosedByDate(final LocalDate date) {

    return loadPort.loadClosedByDate(date).stream()
        .map(mapper::toResponse)
        .sorted(
            DEFAULT_COMPARATOR.thenComparing(DRIVER_COMPARATOR).thenComparing(END_DATE_COMPARATOR))
        .collect(toList());
  }

  @Override
  public Long getCountClosedForCurrentDate() {
    return loadPort.loadCountClosedByDate(qDateTime.getToday());
  }
}
