package ee.qrent.billing.transaction.api.in.query.rent;

import ee.qrent.billing.transaction.api.in.response.rent.RentCalculationResponse;
import java.util.List;

public interface GetRentCalculationQuery {

  List<RentCalculationResponse> getAll();

  RentCalculationResponse getById(final Long id);

  Long getLastCalculatedQWeekId();
}
