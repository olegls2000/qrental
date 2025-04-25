package ee.qrent.billing.driver.core.validator;

import static java.lang.String.format;

import ee.qrent.common.in.validation.*;
import ee.qrent.billing.driver.api.in.request.CallSignAddRequest;
import ee.qrent.billing.driver.api.in.request.CallSignDeleteRequest;
import ee.qrent.billing.driver.api.in.request.CallSignUpdateRequest;
import ee.qrent.billing.driver.api.out.CallSignLinkLoadPort;
import ee.qrent.billing.driver.api.out.CallSignLoadPort;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignRequestValidator
    implements AddRequestValidator<CallSignAddRequest>,
        UpdateRequestValidator<CallSignUpdateRequest>,
        DeleteRequestValidator<CallSignDeleteRequest> {

  private final CallSignLoadPort loadPort;
  private final CallSignLinkLoadPort callSignLinkLoadPort;
  private final AttributeChecker attributeChecker;

  private static final int LENGTH_MAX_COMMENT = 200;
  private static final BigDecimal DECIMAL_MIN_CALL_SIGN = BigDecimal.ONE;
  private static final BigDecimal DECIMAL_MAX_CALL_SIGN = BigDecimal.valueOf(3000);

  @Override
  public ViolationsCollector validate(final CallSignAddRequest request) {
    final var violationsCollector = new ViolationsCollector();
    final var callSign = request.getCallSign();
    checkValidNumber(callSign, violationsCollector);
    checkComment(request.getComment(), violationsCollector);
    checkUniqueness(callSign, violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validate(final CallSignUpdateRequest request) {
    final var violationsCollector = new ViolationsCollector();
    final var callSign = request.getCallSign();
    checkValidNumber(callSign, violationsCollector);
    checkComment(request.getComment(), violationsCollector);
    checkExistence(request.getId(), violationsCollector);
    checkUniqueness(callSign, violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validate(final CallSignDeleteRequest request) {
    final var violationsCollector = new ViolationsCollector();
    checkExistence(request.getId(), violationsCollector);
    checkReferences(request.getId(), violationsCollector);

    return violationsCollector;
  }

  private void checkValidNumber(
      final Integer callSign, final ViolationsCollector violationsCollector) {
    final var attributeName = "Call Sign";
    final var attributeValue = callSign;
    attributeChecker.checkRequired(attributeName, attributeValue, violationsCollector);
    if (attributeValue == null) {
      return;
    }
    attributeChecker.checkDecimalValueRange(
        attributeName,
        BigDecimal.valueOf(attributeValue),
        DECIMAL_MIN_CALL_SIGN,
        DECIMAL_MAX_CALL_SIGN,
        violationsCollector);
  }

  private void checkComment(final String comment, final ViolationsCollector violationsCollector) {
    attributeChecker.checkStringLengthMax(
        "Comment", comment, LENGTH_MAX_COMMENT, violationsCollector);
  }

  private void checkUniqueness(
      final Integer callSign, final ViolationsCollector violationCollector) {
    final var domainFromDb = loadPort.loadByCallSign(callSign);
    if (domainFromDb == null) {
      return;
    }
    violationCollector.collect(format("Call Sign %d already exists", callSign));
  }

  private void checkReferences(final Long id, final ViolationsCollector violationCollector) {
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
      violationCollector.collect("Change of CallSign Link failed. No Record with id = " + id);
    }
  }
}
