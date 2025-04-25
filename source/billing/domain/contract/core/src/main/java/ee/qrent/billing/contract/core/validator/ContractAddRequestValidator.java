package ee.qrent.billing.contract.core.validator;

import static java.lang.String.format;


import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.contract.api.in.request.ContractAddRequest;
import ee.qrent.billing.contract.api.out.ContractLoadPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContractAddRequestValidator implements AddRequestValidator<ContractAddRequest> {

  private final ContractLoadPort loadPort;

  @Override
  public ViolationsCollector validate(final ContractAddRequest request) {
    //TODO: fix number generation
    final var violationsCollector = new ViolationsCollector();
    checkUniqueness(request, violationsCollector);

    return violationsCollector;
  }


  private void checkUniqueness(final ContractAddRequest addRequest, final ViolationsCollector violationCollector) {
    final var number = "ttt";
    final var contractFromDb = loadPort.loadByNumber(number);
    if (contractFromDb == null) {

      return;
    }
    violationCollector.collect(format("Contract with number: %s already exists.", number));
  }

}
