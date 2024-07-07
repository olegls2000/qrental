package ee.qrental.driver.core.validator;

import static java.lang.String.format;

import ee.qrent.common.in.validation.QValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrental.driver.api.out.CallSignLinkLoadPort;
import ee.qrental.driver.api.out.CallSignLoadPort;
import ee.qrental.driver.domain.CallSign;
import java.util.Objects;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignBusinessRuleValidator implements QValidator<CallSign> {

  private final CallSignLoadPort loadPort;
  private final CallSignLinkLoadPort callSignLinkLoadPort;

  @Override
  public ViolationsCollector validateAdd(final CallSign domain) {
    final var violationsCollector = new ViolationsCollector();
    checkUniquenessForAdd(domain, violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateUpdate(final CallSign domain) {
    final var violationsCollector = new ViolationsCollector();
    checkExistence(domain.getId(), violationsCollector);
    checkUniquenessForUpdate(domain, violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateDelete(final Long id) {
    final var violationsCollector = new ViolationsCollector();
    checkReferences(id, violationsCollector);

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
    violationCollector.collect(
        format("Call Sign %d can not be updated, because such Number already in use", callSign));
  }

  private void checkReferences(
      final Long id, final ViolationsCollector violationCollector) {

    final var callSignLinks = callSignLinkLoadPort.loadByCallSignId(id);
    if (callSignLinks.isEmpty()) {
      return;
    }
    violationCollector.collect(
        format(
            "Call Sign with id: %d can not be deleted, because it uses in %d Call Sign Links",
            id, callSignLinks.size()));
  }

  private void checkExistence(final Long id, final ViolationsCollector violationCollector) {
    if (loadPort.loadById(id) == null) {
      violationCollector.collect("Update of CallSign Link failed. No Record with id = " + id);
    }
  }
}
