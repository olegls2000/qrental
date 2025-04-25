package ee.qrent.billing.driver.core.validator;

import static java.lang.String.format;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.DeleteRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.driver.api.in.request.CallSignLinkAddRequest;
import ee.qrent.billing.driver.api.in.request.CallSignLinkDeleteRequest;
import ee.qrent.billing.driver.api.in.request.CallSignLinkUpdateRequest;
import ee.qrent.billing.driver.api.out.CallSignLinkLoadPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignLinkRequestValidator
    implements AddRequestValidator<CallSignLinkAddRequest>,
        UpdateRequestValidator<CallSignLinkUpdateRequest>,
        DeleteRequestValidator<CallSignLinkDeleteRequest> {

  private final CallSignLinkLoadPort loadPort;

  @Override
  public ViolationsCollector validate(final CallSignLinkAddRequest request) {
    final var violationsCollector = new ViolationsCollector();
    checkIsCallSignFree(request, violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validate(final CallSignLinkUpdateRequest request) {
    final var violationsCollector = new ViolationsCollector();
    checkExistence(request.getId(), violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validate(final CallSignLinkDeleteRequest request) {
    final var violationsCollector = new ViolationsCollector();

    return violationsCollector;
  }

  private void checkIsCallSignFree(
      final CallSignLinkAddRequest request, final ViolationsCollector violationCollector) {
    final var callSignId = request.getCallSignId();
    final var activeCallSignLink = loadPort.loadActiveByCallSignId(callSignId);
    if (activeCallSignLink != null) {
      violationCollector.collect(
          format(
              "Call Sign id: '%d' already uses by active Link and can not be linked.", callSignId));
    }
  }

  private void checkExistence(final Long id, final ViolationsCollector violationCollector) {
    if (loadPort.loadById(id) == null) {
      violationCollector.collect("Update of CallSign Link failed. No Record with id = " + id);
    }
  }
}
