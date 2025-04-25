package ee.qrent.billing.contract.core.validator;

import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.contract.api.in.request.ContractUpdateRequest;
import ee.qrent.billing.contract.api.out.ContractLoadPort;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContractUpdateRequestValidator
    implements UpdateRequestValidator<ContractUpdateRequest> {

  private final ContractLoadPort loadPort;

  @Override
  public ViolationsCollector validate(final ContractUpdateRequest request) {
    final var violationsCollector = new ViolationsCollector();
    checkExistence(request.getId(), violationsCollector);

    return violationsCollector;
  }

  private void checkExistence(final Long id, final ViolationsCollector violationCollector) {
    if (loadPort.loadById(id) == null) {
      violationCollector.collect("Update of Contract failed. No Record with id = " + id);
    }
  }
}
