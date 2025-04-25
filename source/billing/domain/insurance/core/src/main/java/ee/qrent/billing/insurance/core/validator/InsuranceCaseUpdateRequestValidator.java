package ee.qrent.billing.insurance.core.validator;

import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.insurance.api.in.request.InsuranceCaseUpdateRequest;
import ee.qrent.billing.insurance.api.out.InsuranceCaseBalanceLoadPort;
import ee.qrent.billing.insurance.api.out.InsuranceCaseLoadPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseUpdateRequestValidator
    implements UpdateRequestValidator<InsuranceCaseUpdateRequest> {

  private final InsuranceCaseLoadPort insuranceCaseLoadPort;
  private final InsuranceCaseBalanceLoadPort insuranceCaseBalanceLoadPort;

  @Override
  public ViolationsCollector validate(final InsuranceCaseUpdateRequest request) {
    final var violationsCollector = new ViolationsCollector();
    final var id = request.getId();

    if (insuranceCaseLoadPort.loadById(id) == null) {
      violationsCollector.collect("Update of InsuranceCase failed. No Record with id = " + id);

      return violationsCollector;
    }

    final var latestBalance = insuranceCaseBalanceLoadPort.loadLatestByInsuranceCaseId(id);
    if (latestBalance != null) {
      violationsCollector.collect(
          "Balance calculation for current Insurance Case already started. Modification is prohibited.");
    }

    return violationsCollector;
  }
}
