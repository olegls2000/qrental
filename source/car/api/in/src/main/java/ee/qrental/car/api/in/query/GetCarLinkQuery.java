package ee.qrental.car.api.in.query;

import ee.qrental.car.api.in.request.CarLinkUpdateRequest;
import ee.qrental.car.api.in.response.CarLinkResponse;
import ee.qrental.common.core.in.query.BaseGetQuery;


public interface GetCarLinkQuery extends BaseGetQuery<CarLinkUpdateRequest, CarLinkResponse> {
   CarLinkResponse getActiveLinkByDriverId(final Long driverId);
}
