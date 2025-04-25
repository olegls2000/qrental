package ee.qrent.billing.driver.core.validator;

import ee.qrent.common.in.validation.AttributeChecker;
import ee.qrent.common.core.validation.AttributeCheckerImpl;
import ee.qrent.billing.driver.api.in.request.CallSignAddRequest;
import ee.qrent.billing.driver.api.in.request.CallSignDeleteRequest;
import ee.qrent.billing.driver.api.in.request.CallSignUpdateRequest;
import ee.qrent.billing.driver.api.out.CallSignLinkLoadPort;
import ee.qrent.billing.driver.api.out.CallSignLoadPort;
import ee.qrent.billing.driver.domain.CallSign;
import ee.qrent.billing.driver.domain.CallSignLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CallSignRequestValidatorTest {

  private CallSignRequestValidator instanceUnderTest;
  private CallSignLoadPort loadPort;
  private CallSignLinkLoadPort linkLoadPort;
  private AttributeChecker attributeChecker;

  @BeforeEach
  void init() {
    loadPort = mock(CallSignLoadPort.class);
    linkLoadPort = mock(CallSignLinkLoadPort.class);
    attributeChecker = new AttributeCheckerImpl();
    instanceUnderTest = new CallSignRequestValidator(loadPort, linkLoadPort, attributeChecker);
  }

  @Test
  public void testIfCallSignAddRequestWithNullCallSignAndInvalidComment() {
    // given
    final var addRequest = new CallSignAddRequest();
    addRequest.setCallSign(null);
    addRequest.setComment(
        "201_Characters_123456789_123456789_123456789_"
            + "123456789_123456789_123456789_123456789_123456789_123456789_"
            + "23456789_123456789_123456789_123456789_123456789_123456789_"
            + "123456789_123456789_123456789_1234567");

    when(loadPort.loadByCallSign(addRequest.getCallSign())).thenReturn(null);

    // when
    final var violationCollector = instanceUnderTest.validate(addRequest);

    // then
    assertTrue(violationCollector.hasViolations());
    assertEquals(2, violationCollector.getViolations().size());
    assertTrue(
        violationCollector.getViolations().stream()
            .anyMatch(
                violation -> violation.equals("Invalid value for Call Sign. Value must be set")));
    assertTrue(
        violationCollector.getViolations().stream()
            .anyMatch(
                violation ->
                    violation.equals(
                        "Invalid value for Comment. Current length: 201. Valid length must be not more then: 200")));
  }

  //@Test
  public void testIfCallSignAddRequestWithCallSignNotInRange() {
    // given
    final var addRequest = new CallSignAddRequest();
    addRequest.setCallSign(1000);
    when(loadPort.loadByCallSign(addRequest.getCallSign())).thenReturn(null);

    // when
    final var violationCollector = instanceUnderTest.validate(addRequest);

    // then
    assertTrue(violationCollector.hasViolations());
    assertEquals(1, violationCollector.getViolations().size());
    assertTrue(
        violationCollector.getViolations().stream()
            .anyMatch(
                violation ->
                    violation.equals(
                        "Invalid value for Call Sign. Valid value must be in a range: [1 ... 999]")));
  }

  @Test
  public void testIfCallSignAddRequestWithCallSignAlreadyInUse() {
    // given
    final var addRequest = new CallSignAddRequest();
    addRequest.setCallSign(5);
    when(loadPort.loadByCallSign(addRequest.getCallSign())).thenReturn(CallSign.builder().build());

    // when
    final var violationCollector = instanceUnderTest.validate(addRequest);

    // then
    assertTrue(violationCollector.hasViolations());
    assertEquals(1, violationCollector.getViolations().size());
    assertTrue(
        violationCollector.getViolations().stream()
            .anyMatch(violation -> violation.equals("Call Sign 5 already exists")));
  }

  @Test
  public void testIfCallSignUpdateRequestHasUnexistentId() {
    // given
    final var updateRequest = new CallSignUpdateRequest();
    updateRequest.setId(11l);
    updateRequest.setCallSign(5);
    when(loadPort.loadById(11L)).thenReturn(null);

    // when
    final var violationCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertTrue(violationCollector.hasViolations());
    assertEquals(1, violationCollector.getViolations().size());
    assertTrue(
        violationCollector.getViolations().stream()
            .anyMatch(
                violation ->
                    violation.equals("Change of CallSign Link failed. No Record with id = 11")));
  }

  @Test
  public void testIfCallSignUpdateRequestWithNullCallSignAndInvalidComment() {
    // given
    final var updateRequest = new CallSignUpdateRequest();
    updateRequest.setId(11l);
    updateRequest.setCallSign(null);
    updateRequest.setComment(
        "201_Characters_123456789_123456789_123456789_"
            + "123456789_123456789_123456789_123456789_123456789_123456789_"
            + "23456789_123456789_123456789_123456789_123456789_123456789_"
            + "123456789_123456789_123456789_1234567");

    when(loadPort.loadById(updateRequest.getId())).thenReturn(CallSign.builder().build());
    when(loadPort.loadByCallSign(updateRequest.getCallSign())).thenReturn(null);

    // when
    final var violationCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertTrue(violationCollector.hasViolations());
    assertEquals(2, violationCollector.getViolations().size());
    assertTrue(
        violationCollector.getViolations().stream()
            .anyMatch(
                violation -> violation.equals("Invalid value for Call Sign. Value must be set")));
    assertTrue(
        violationCollector.getViolations().stream()
            .anyMatch(
                violation ->
                    violation.equals(
                        "Invalid value for Comment. Current length: 201. Valid length must be not more then: 200")));
  }

  //@Test
  public void testIfCallSignUpdateRequestWithCallSignNotInRange() {
    // given
    final var updateRequest = new CallSignUpdateRequest();
    updateRequest.setId(11l);
    updateRequest.setCallSign(1000);
    when(loadPort.loadByCallSign(updateRequest.getCallSign())).thenReturn(null);
    when(loadPort.loadById(updateRequest.getId())).thenReturn(CallSign.builder().build());

    // when
    final var violationCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertTrue(violationCollector.hasViolations());
    assertEquals(1, violationCollector.getViolations().size());
    assertTrue(
        violationCollector.getViolations().stream()
            .anyMatch(
                violation ->
                    violation.equals(
                        "Invalid value for Call Sign. Valid value must be in a range: [1 ... 999]")));
  }

  @Test
  public void testIfCallSignUpdateRequestWithCallSignAlreadyInUse() {
    // given
    final var updateRequest = new CallSignUpdateRequest();
    updateRequest.setId(11l);
    updateRequest.setCallSign(5);
    when(loadPort.loadByCallSign(updateRequest.getCallSign()))
        .thenReturn(CallSign.builder().build());
    when(loadPort.loadById(updateRequest.getId())).thenReturn(CallSign.builder().build());

    // when
    final var violationCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertTrue(violationCollector.hasViolations());
    assertEquals(1, violationCollector.getViolations().size());
    assertTrue(
        violationCollector.getViolations().stream()
            .anyMatch(violation -> violation.equals("Call Sign 5 already exists")));
  }

  @Test
  public void testIfCallSignDeleteRequestHasUnexistentId() {
    // given
    final var deleteRequest = new CallSignDeleteRequest(11l);
    when(loadPort.loadById(11L)).thenReturn(null);

    // when
    final var violationCollector = instanceUnderTest.validate(deleteRequest);

    // then
    assertTrue(violationCollector.hasViolations());
    assertEquals(1, violationCollector.getViolations().size());
    assertTrue(
        violationCollector.getViolations().stream()
            .anyMatch(
                violation ->
                    violation.equals("Change of CallSign Link failed. No Record with id = 11")));
  }

  @Test
  public void testIfCallSignDeleteRequestHasReferences() {
    // given
    final var deleteRequest = new CallSignDeleteRequest(11l);
    when(loadPort.loadById(11L)).thenReturn(CallSign.builder().build());
    when(linkLoadPort.loadByCallSignId(11L))
        .thenReturn(singletonList(CallSignLink.builder().build()));

    // when
    final var violationCollector = instanceUnderTest.validate(deleteRequest);

    // then
    assertTrue(violationCollector.hasViolations());
    assertEquals(1, violationCollector.getViolations().size());
    assertTrue(
        violationCollector.getViolations().stream()
            .anyMatch(
                violation ->
                    violation.equals(
                        "Call Sign with id: 11 can not be deleted, because it uses in 1 Call Sign Links")));
  }
}
