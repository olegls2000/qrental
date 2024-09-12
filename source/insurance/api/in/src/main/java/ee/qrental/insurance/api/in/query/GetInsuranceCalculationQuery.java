package ee.qrental.insurance.api.in.query;

import ee.qrental.insurance.api.in.response.InsuranceCalculationResponse;
import java.util.List;

public interface GetInsuranceCalculationQuery {

  List<InsuranceCalculationResponse> getAll();

  InsuranceCalculationResponse getById(final Long id);

  Long getStartQWeekId();

  Long getLastCalculatedQWeekId();
}
