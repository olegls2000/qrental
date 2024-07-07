package ee.qrental.driver.core.validator;

import static java.lang.String.format;

import ee.qrent.common.in.validation.QValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrental.driver.api.out.CallSignLinkLoadPort;
import ee.qrental.driver.domain.CallSignLink;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignLinkBusinessRuleValidator implements QValidator<CallSignLink> {

  private final CallSignLinkLoadPort loadPort;

  @Override
  public ViolationsCollector validateAdd(CallSignLink domain) {
    final var violationsCollector = new ViolationsCollector();
    checkIsCallSignFree(domain, violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateUpdate(CallSignLink domain) {
    final var violationsCollector = new ViolationsCollector();
    checkExistence(domain.getId(), violationsCollector);
    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateDelete(final Long id) {
    final var violationsCollector = new ViolationsCollector();
    return violationsCollector;
  }

  private void checkIsCallSignFree(
      final CallSignLink domain, final ViolationsCollector violationCollector) {
    final var callSignId = domain.getCallSign().getId();
    final var callSign = domain.getCallSign().getCallSign();
    final var activeCallSignLink = loadPort.loadActiveByCallSignId(callSignId);
    if (activeCallSignLink != null) {
      violationCollector.collect(
          format("Call Sign: '%d' already uses by active Link and can not be linked.", callSign));
    }
  }

  private void checkExistence(final Long id, final ViolationsCollector violationCollector) {
    if (loadPort.loadById(id) == null) {
      violationCollector.collect("Update of CallSign Link failed. No Record with id = " + id);
    }
  }
}
