package ee.qrental.insurance.core.validator;

import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.insurance.api.in.request.InsuranceCalculationAddRequest;
import ee.qrental.transaction.api.in.query.rent.GetRentCalculationQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCalculationAddBusinessRuleValidator {
  private final GetQWeekQuery qWeekQuery;
  private final GetRentCalculationQuery rentCalculationQuery;

  public ViolationsCollector validateAdd(final InsuranceCalculationAddRequest request) {
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
