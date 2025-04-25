package ee.qrent.billing.contract.core.validator;

import ee.qrent.common.in.validation.CloseRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.contract.api.in.request.ContractCloseRequest;
import ee.qrent.billing.contract.api.out.ContractLoadPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContractCloseRequestValidator implements CloseRequestValidator<ContractCloseRequest> {

  private final ContractLoadPort contractLoadPort;

  @Override
  public ViolationsCollector validate(final ContractCloseRequest request) {
    final var violationsCollector = new ViolationsCollector();
    final var requestId = request.getId();
    // TODO what to check
    final var contractToClose = contractLoadPort.loadById(requestId);

    if (contractToClose == null) {
      violationsCollector.collect("Contract with id " + requestId + " not found");
    }
    return violationsCollector;
  }
}
