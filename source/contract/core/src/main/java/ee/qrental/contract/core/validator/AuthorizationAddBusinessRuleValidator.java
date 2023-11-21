package ee.qrental.contract.core.validator;

import static java.lang.String.format;

import ee.qrental.common.core.in.validation.ViolationsCollector;
import ee.qrental.contract.api.in.request.AuthorizationAddRequest;
import ee.qrental.contract.api.out.AuthorizationLoadPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationAddBusinessRuleValidator {

  private final AuthorizationLoadPort loadPort;


  public ViolationsCollector validateAdd(final AuthorizationAddRequest addRequest) {
    final var violationsCollector = new ViolationsCollector();
    checkUniqueness(addRequest, violationsCollector);

    return violationsCollector;
  }
  private void checkUniqueness(final AuthorizationAddRequest addRequest, final ViolationsCollector violationCollector) {
    final var driverId = addRequest.getDriverId();
    final var fromDb = loadPort.loadByDriverId(driverId);
    if (fromDb == null) {

      return;
    }
    violationCollector.collect(format("Bolt Authority for Driver with id: %d already exists", driverId));
  }

  private void checkExistence(final Long id, final ViolationsCollector violationCollector) {
    if (loadPort.loadById(id) == null) {
      violationCollector.collect("Update of Contract failed. No Record with id = " + id);
    }
  }
}
