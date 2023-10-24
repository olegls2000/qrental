package ee.qrental.car.api.in.query;

import ee.qrental.car.api.in.request.CarLinkUpdateRequest;
import ee.qrental.car.api.in.response.CarLinkResponse;
import ee.qrental.common.core.in.query.BaseGetQuery;

import java.time.LocalDate;
import java.util.List;

public interface GetCarLinkQuery extends BaseGetQuery<CarLinkUpdateRequest, CarLinkResponse> {
   CarLinkResponse getActiveLinkByDriverId(final Long driverId);

   List<CarLinkResponse> getActiveByDate(final LocalDate date);
}
