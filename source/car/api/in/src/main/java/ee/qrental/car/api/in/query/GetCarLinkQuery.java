package ee.qrental.car.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrental.car.api.in.request.CarLinkUpdateRequest;
import ee.qrental.car.api.in.response.CarLinkResponse;

import java.time.LocalDate;
import java.util.List;

public interface GetCarLinkQuery extends BaseGetQuery<CarLinkUpdateRequest, CarLinkResponse> {
  CarLinkResponse getActiveLinkByDriverId(final Long driverId);

  CarLinkResponse getFirstLinkByDriverId(final Long driverId);

  List<CarLinkResponse> getActive();

  List<CarLinkResponse> getClosedByDate(final LocalDate date);

  Long getCountActive();

  Long getCountClosed();
}
