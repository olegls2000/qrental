package ee.qrent.billing.insurance.core.validator;

import ee.qrent.common.in.validation.CloseRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.insurance.api.in.request.InsuranceCaseCloseRequest;
import ee.qrent.billing.insurance.api.out.InsuranceCaseLoadPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseCloseRequestValidator
    implements CloseRequestValidator<InsuranceCaseCloseRequest> {

  private final InsuranceCaseLoadPort loadPort;

  public ViolationsCollector validate(final InsuranceCaseCloseRequest request) {
    final var violationsCollector = new ViolationsCollector();
    final var requestId = request.getId();

    final var insuranceCaseToClose = loadPort.loadById(requestId);

    if (!insuranceCaseToClose.getActive()) {
      violationsCollector.collect("Insurance Case already closed");
    }
    return violationsCollector;
  }
}
