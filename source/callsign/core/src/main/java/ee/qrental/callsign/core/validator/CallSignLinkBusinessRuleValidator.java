package ee.qrental.callsign.core.validator;

import static java.lang.String.format;

import ee.qrental.callsign.api.out.CallSignLinkLoadPort;
import ee.qrental.callsign.api.out.CallSignLoadPort;
import ee.qrental.callsign.domain.CallSignLink;
import ee.qrental.common.core.in.validation.QValidator;
import ee.qrental.common.core.in.validation.ViolationsCollector;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignLinkBusinessRuleValidator implements QValidator<CallSignLink> {

  private final CallSignLinkLoadPort callSignLinkLoadPort;
  private final CallSignLoadPort callSignLoadPort;
  private final GetDriverQuery driverQuery;

  @Override
  public ViolationsCollector validate(final CallSignLink domain) {
    final var violationsCollector = new ViolationsCollector();
    final var activeCallSignLinks = callSignLinkLoadPort.loadActiveCallSignLinks();
    checkCallSign(domain, violationsCollector, activeCallSignLinks);
    checkDriver(domain, violationsCollector, activeCallSignLinks);

    return violationsCollector;
  }

  private void checkCallSign(
      final CallSignLink domain,
      final ViolationsCollector violationCollector,
      final List<CallSignLink> activeCallSignLinks) {
    final var callSignId = domain.getCallSign().getId();
    final var callSignLinkByCallSignOpt =
        activeCallSignLinks.stream()
            .filter(csl -> callSignId.equals(csl.getCallSign().getId()))
            .findFirst();
    if (callSignLinkByCallSignOpt.isEmpty()) {
      return;
    }
    if (callSignLinkByCallSignOpt.get().getId().equals(domain.getId())) {
      return;
    }
    final var callSign = callSignLoadPort.loadById(callSignId);
    violationCollector.collect(
        format("Call Sign: '%d' is active and can not be linked.", callSign.getCallSign()));
  }

  private void checkDriver(
      final CallSignLink domain,
      final ViolationsCollector violationCollector,
      final List<CallSignLink> activeCallSignLinks) {
    final var driverId = domain.getDriverId();
    final var callSignLinkByDriverOpt =
        activeCallSignLinks.stream()
            .filter(callSignLink -> callSignLink.getDriverId().equals(driverId))
            .findFirst();
    if (callSignLinkByDriverOpt.isEmpty()) {
      return;
    }
    if (callSignLinkByDriverOpt.get().getId().equals(domain.getId())) {
      return;
    }
    violationCollector.collect(
        format("Driver: %s already has an active Call Sign.", driverQuery.getObjectInfo(driverId)));
  }
}
