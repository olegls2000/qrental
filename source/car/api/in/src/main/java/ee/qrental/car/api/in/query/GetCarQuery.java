package ee.qrental.car.api.in.query;

import ee.qrental.car.api.in.request.CarUpdateRequest;
import ee.qrental.car.api.in.response.CarResponse;
import ee.qrental.common.core.in.query.BaseGetQuery;

import java.util.List;

public interface GetCarQuery extends BaseGetQuery<CarUpdateRequest, CarResponse> {
    List<CarResponse> getAvailableCars();
}
