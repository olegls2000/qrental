package ee.qrental.driver.core.validator;

import ee.qrental.driver.api.out.CallSignLinkLoadPort;
import ee.qrental.driver.api.out.CallSignLoadPort;
import ee.qrental.driver.domain.CallSign;
import ee.qrental.driver.domain.CallSignLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CallSignBusinessRuleValidatorTest {
  private CallSignBusinessRuleValidator instanceUnderTest;
  private CallSignLoadPort loadPort;
  private CallSignLinkLoadPort callSignLinkLoadPort;

  @BeforeEach
  void init() {
    loadPort = mock(CallSignLoadPort.class);
    callSignLinkLoadPort = mock(CallSignLinkLoadPort.class);
    instanceUnderTest = new CallSignBusinessRuleValidator(loadPort, callSignLinkLoadPort);
  }

  @Test
  public void testDomainValidateAdd() {
    // given
    final long id = 555L;
    final int callSign = 4;

    final var domain = CallSign.builder().id(id).callSign(callSign).comment("Test").build();

    // when
    final var violationCollector = instanceUnderTest.validateAdd(domain);

    // then
    assertFalse(violationCollector.hasViolations());
  }

  @Test
  public void testDomainUniquenessValidateAdd() {
    // given
    final long id = 555L;
    final int callSign = 4;

    final var domain = CallSign.builder().id(id).callSign(callSign).comment("Test").build();

    when(loadPort.loadByCallSign(4)).thenReturn(CallSign.builder().id(555L).callSign(4).build());

    // when
    final var violationCollector = instanceUnderTest.validateAdd(domain);

    // then
    assertTrue(violationCollector.hasViolations());
    final var violation = violationCollector.getViolations().get(0);
    assertEquals("Call Sign 4 already exists", violation);
  }

  @Test
  public void testDomainValidateDeleteCallSignLinksIsNotEmpty() {
    // given
    final var idToAdd = 6L;

    when(callSignLinkLoadPort.loadByCallSignId(idToAdd))
        .thenReturn(Collections.singletonList(CallSignLink.builder().id(idToAdd).build()));

    // when
    final var violationCollector = instanceUnderTest.validateDelete(idToAdd);

    // then
    assertTrue(violationCollector.hasViolations());
    final var violation = violationCollector.getViolations().get(0);
    assertEquals(
        "Call Sign with id: 6 can not be deleted, because it uses in 1 Call Sign Links", violation);
  }

  @Test
  public void testDomainValidateDeleteCallSignLinksIsEmpty() {
    // given
    final var idToAdd = 6L;

    when(callSignLinkLoadPort.loadByCallSignId(idToAdd)).thenReturn(emptyList());

    // when
    final var violationCollector = instanceUnderTest.validateDelete(idToAdd);

    // then
    assertFalse(violationCollector.hasViolations());
  }

  @Test
  public void testDomainValidateUpdate() {
    // given
    final long id = 555L;
    final int callSign = 4;

    final var domain = CallSign.builder().id(id).callSign(callSign).comment("Test").build();

    when(loadPort.loadById(id)).thenReturn(CallSign.builder().id(id).build());
    when(callSignLinkLoadPort.loadById(id)).thenReturn(CallSignLink.builder().id(id).build());

    // when
    final var violationCollector = instanceUnderTest.validateUpdate(domain);

    // then
    assertFalse(violationCollector.hasViolations());
  }

  @Test
  public void testDomainValidateUpdateCheckExistence() {
    // given
    final long id = 5L;
    final int callSign = 4;

    final var domain = CallSign.builder().id(id).callSign(callSign).comment("Test").build();

    when(loadPort.loadById(id)).thenReturn(null);

    // when
    final var violationCollector = instanceUnderTest.validateUpdate(domain);

    // then
    assertTrue(violationCollector.hasViolations());
    final var violation = violationCollector.getViolations().get(0);
    assertEquals("Update of CallSign Link failed. No Record with id = 5", violation);
  }

  @Test
  public void testDomainValidateUpdateCheckUniquenessDifferenceDriverIdToUpdate() {
    // given
    final var id = 5L;
    final var callSign = 4;
    final var domain = CallSign.builder().id(id).callSign(callSign).comment("Test").build();

    when(loadPort.loadByCallSign(domain.getCallSign()))
        .thenReturn(CallSign.builder().id(6L).build());

    // when
    final var violationCollector = instanceUnderTest.validateUpdate(domain);

    // then
    assertTrue(violationCollector.hasViolations());
    final var violation = violationCollector.getViolations().get(0);
    assertEquals("Update of CallSign Link failed. No Record with id = 5", violation);
  }

  @Test
  public void testDomainValidateUpdateCheckUniquenessNotDifferenceDriverIdToUpdate() {
    // given
    final var id = 5L;
    final var callSign = 4;

    final var domain = CallSign.builder().id(id).callSign(callSign).comment("Test").build();

    when(loadPort.loadByCallSign(domain.getCallSign()))
        .thenReturn(CallSign.builder().id(id).build());

    // when
    final var violationCollector = instanceUnderTest.validateUpdate(domain);

    // then
    assertTrue(violationCollector.hasViolations());
    final var violation = violationCollector.getViolations().get(0);
    assertEquals("Update of CallSign Link failed. No Record with id = 5", violation);
  }
}
