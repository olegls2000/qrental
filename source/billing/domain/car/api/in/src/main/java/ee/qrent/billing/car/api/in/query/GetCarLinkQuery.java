package ee.qrent.billing.car.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrent.billing.car.api.in.request.CarLinkUpdateRequest;
import ee.qrent.billing.car.api.in.response.CarLinkResponse;

import java.time.LocalDate;
import java.util.List;

public interface GetCarLinkQuery extends BaseGetQuery<CarLinkUpdateRequest, CarLinkResponse> {
  CarLinkResponse getActiveLinkByDriverId(final Long driverId);

  CarLinkResponse getFirstLinkByDriverId(final Long driverId);

  List<CarLinkResponse> getAllActiveForCurrentDate();

  List<CarLinkResponse> getAllActiveByQWeekId(final Long weekId);

  List<CarLinkResponse> getClosedByDate(final LocalDate date);

  Long getCountActiveForCurrentDate();

  Long getCountClosedForCurrentDate();
}
