package ee.qrental.car.api.in.query;

import ee.qrental.car.api.in.query.filter.CarFilter;
import ee.qrental.car.api.in.request.CarUpdateRequest;
import ee.qrental.car.api.in.response.CarResponse;
import ee.qrental.common.core.in.query.BaseGetQuery;

import java.util.List;
import java.util.Map;

public interface GetCarQuery extends BaseGetQuery<CarUpdateRequest, CarResponse> {
  List<CarResponse> getAvailableCars();

  List<CarResponse> getAllByFilter(final CarFilter filterRequest);

  Map<String, String> getAllStatuses();
}
