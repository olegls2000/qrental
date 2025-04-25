package ee.qrent.billing.car.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrent.billing.car.api.in.query.filter.CarFilter;
import ee.qrent.billing.car.api.in.request.CarUpdateRequest;
import ee.qrent.billing.car.api.in.response.CarResponse;

import java.util.List;
import java.util.Map;

public interface GetCarQuery extends BaseGetQuery<CarUpdateRequest, CarResponse> {
  List<CarResponse> getAvailableCars();

  List<CarResponse> getAllByFilter(final CarFilter filterRequest);

  Map<String, String> getAllStatuses();
}
