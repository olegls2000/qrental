package ee.qrental.callsign.core.validator;

import static java.lang.String.format;

import ee.qrental.callsign.api.out.CallSignLinkLoadPort;
import ee.qrental.callsign.api.out.CallSignLoadPort;
import ee.qrental.callsign.domain.CallSign;
import ee.qrental.common.core.in.validation.QValidator;
import ee.qrental.common.core.in.validation.ViolationsCollector;
import java.util.Objects;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignBusinessRuleValidator implements QValidator<CallSign> {

  private final CallSignLoadPort loadPort;
  private final CallSignLinkLoadPort callSignLinkLoadPort;

  @Override
  public ViolationsCollector validateAdd(CallSign domain) {
    final var violationsCollector = new ViolationsCollector();
    checkUniquenessForAdd(domain, violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateUpdate(CallSign domain) {
    final var violationsCollector = new ViolationsCollector();
    checkExistence(domain.getId(), violationsCollector);
    checkUniquenessForUpdate(domain, violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateDelete(CallSign domain) {
    final var violationsCollector = new ViolationsCollector();
    checkReferences(domain, violationsCollector);

    return violationsCollector;
  }

  private void checkUniquenessForAdd(
      final CallSign domain, final ViolationsCollector violationCollector) {
    final var callSign = domain.getCallSign();
    final var domainFromDb = loadPort.loadByCallSign(callSign);
    if (domainFromDb == null) {
      return;
    }

    violationCollector.collect(format("Call Sign %d already exists", callSign));
  }

  private void checkUniquenessForUpdate(
      final CallSign domain, final ViolationsCollector violationCollector) {
    final var callSign = domain.getCallSign();
    final var domainFromDb = loadPort.loadByCallSign(callSign);
    if (domainFromDb == null) {
      return;
    }
    if (Objects.equals(domainFromDb.getId(), domain.getId())) {
      return;
    }
    violationCollector.collect(format("Call Sign %d can not be updated, because such Number already in use", callSign));
  }

  private void checkReferences(
      final CallSign domain, final ViolationsCollector violationCollector) {
    final var callSignId = domain.getId();
    final var callSignLinks = callSignLinkLoadPort.loadByCallSignId(callSignId);
    if (callSignLinks.isEmpty()) {
      return;
    }
    violationCollector.collect(
        format("Call Sign: %d can not be deleted, because it uses in %d Call Sign Links", domain.getCallSign(), callSignLinks.size()));
  }

  private void checkExistence(final Long id, final ViolationsCollector violationCollector) {
    if (loadPort.loadById(id) == null) {
      violationCollector.collect("Update of CallSign Link failed. No Record with id = " + id);
    }
  }
}
