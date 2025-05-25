package ee.qrent.billing.insurance.api.in.query;

import ee.qrent.billing.insurance.api.in.response.InsuranceCalculationResponse;
import java.util.List;

// TODO extent BaseGetQuery
public interface GetInsuranceCalculationQuery {

  List<InsuranceCalculationResponse> getAll();

  InsuranceCalculationResponse getById(final Long id);

  Long getStartQWeekId();

  Long getLastCalculatedQWeekId();
}
