package ee.qrental.car.api.in.query;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.car.api.in.request.CarUpdateRequest;
import ee.qrental.car.api.in.response.CarResponse;

public interface GetCarQuery extends BaseGetQuery<CarUpdateRequest, CarResponse> {}
