package ee.qrental.driver.core.validator;

import ee.qrental.driver.api.out.CallSignLinkLoadPort;
import ee.qrental.driver.api.out.CallSignLoadPort;
import ee.qrental.driver.domain.CallSign;
import ee.qrental.driver.domain.CallSignLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

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

        final var domain = CallSign.builder()
                .id(id)
                .callSign(callSign)
                .comment("Test")
                .build();

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

        final var domain = CallSign.builder()
                .id(id)
                .callSign(callSign)
                .comment("Test")
                .build();

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

        when(callSignLinkLoadPort.loadByCallSignId(idToAdd)).thenReturn(Collections.singletonList(CallSignLink.builder().id(idToAdd).build()));

        // when
        final var violationCollector = instanceUnderTest.validateDelete(idToAdd);

        // then
        assertTrue(violationCollector.hasViolations());
    }

    @Test
    public void testDomainValidateDeleteCallSignLinksIsEmpty() {
        // given
        final var idToAdd = 6L;

        when(callSignLinkLoadPort.loadByCallSignId(idToAdd)).thenReturn(Collections.emptyList());

        // when
        final var violationCollector = instanceUnderTest.validateDelete(idToAdd);

        // then
        assertFalse(violationCollector.hasViolations());
    }

    @Test
    public void testDomainValidateUpdate() {
        // given
        final var driverIdToAdd = 5L;
        final var callSignId = 4;

        final var domain = CallSign.builder()
                .id(driverIdToAdd)
                .callSign(callSignId)
                .comment("Test")
                .build();

        when(loadPort.loadById(driverIdToAdd)).thenReturn(CallSign.builder().id(driverIdToAdd).build());
        when(callSignLinkLoadPort.loadById(driverIdToAdd)).thenReturn(CallSignLink.builder().id(driverIdToAdd).build());

        // when
        final var violationCollector = instanceUnderTest.validateUpdate(domain);

        // then
        assertFalse(violationCollector.hasViolations());
        assertEquals(domain.getId(), driverIdToAdd);
        assertEquals(domain.getCallSign(), callSignId);
        assertEquals(domain.getComment(), "Test");
    }

    @Test
    public void testDomainValidateUpdateCheckExistence() {
        // given
        final var driverIdToAdd = 5L;
        final var callSignId = 4;

        final var domain = CallSign.builder()
                .id(driverIdToAdd)
                .callSign(callSignId)
                .comment("Test")
                .build();

        when(loadPort.loadById(driverIdToAdd)).thenReturn(null);

        // when
        final var violationCollector = instanceUnderTest.validateUpdate(domain);

        // then
        assertTrue(violationCollector.hasViolations());
        assertEquals(domain.getId(), driverIdToAdd);
        assertEquals(domain.getCallSign(), callSignId);
        assertEquals(domain.getComment(), "Test");
    }

    @Test
    public void testDomainValidateUpdateCheckUniquenessDifferenceDriverIdToUpdate() {
        // given
        final var driverIdToAdd = 5L;
        final var callSignId = 4;

        final var domain = CallSign.builder()
                .id(driverIdToAdd)
                .callSign(callSignId)
                .comment("Test")
                .build();

        when(loadPort.loadByCallSign(domain.getCallSign())).thenReturn(CallSign.builder().id(6L).build());

        // when
        final var violationCollector = instanceUnderTest.validateUpdate(domain);

        // then
        assertTrue(violationCollector.hasViolations());
        assertEquals(domain.getId(), driverIdToAdd);
        assertEquals(domain.getCallSign(), callSignId);
        assertEquals(domain.getComment(), "Test");
    }

    @Test
    public void testDomainValidateUpdateCheckUniquenessNotDifferenceDriverIdToUpdate() {
        // given
        final var driverIdToUpdate = 5L;
        final var callSignId = 4;

        final var domain = CallSign.builder()
                .id(driverIdToUpdate)
                .callSign(callSignId)
                .comment("Test")
                .build();

        when(loadPort.loadByCallSign(domain.getCallSign())).thenReturn(CallSign.builder().id(driverIdToUpdate).build());

        // when
        final var violationCollector = instanceUnderTest.validateUpdate(domain);

        // then
        assertTrue(violationCollector.hasViolations());
        assertEquals(domain.getId(), driverIdToUpdate);
        assertEquals(domain.getCallSign(), callSignId);
        assertEquals(domain.getComment(), "Test");
    }
}