package ee.qrent.billing.insurance.core.validator;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.insurance.api.in.request.InsuranceCalculationAddRequest;
import ee.qrent.billing.transaction.api.in.query.rent.GetRentCalculationQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCalculationAddRequestValidator
    implements AddRequestValidator<InsuranceCalculationAddRequest> {
  private final GetQWeekQuery qWeekQuery;
  private final GetRentCalculationQuery rentCalculationQuery;

  @Override
  public ViolationsCollector validate(final InsuranceCalculationAddRequest request) {
    final var violationsCollector = new ViolationsCollector();
    checkIfRentCalculationIsDone(request, violationsCollector);

    return violationsCollector;
  }

  private void checkIfRentCalculationIsDone(
      final InsuranceCalculationAddRequest request, final ViolationsCollector violationCollector) {

    final var latestRentCalculationQWeekId = rentCalculationQuery.getLastCalculatedQWeekId();
    final var latestRentCalculationQWeek = qWeekQuery.getById(latestRentCalculationQWeekId);
    final var requestedQWeek = qWeekQuery.getById(request.getQWeekId());
    if (requestedQWeek.compareTo(latestRentCalculationQWeek) > 0) {
      violationCollector.collect(
          "Rent calculation must be done, before Insurance Balance calculations");
    }
  }
}
