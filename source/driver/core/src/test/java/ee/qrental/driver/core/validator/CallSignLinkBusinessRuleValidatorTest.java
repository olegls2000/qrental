package ee.qrental.driver.core.validator;

import ee.qrental.driver.api.out.CallSignLinkLoadPort;
import ee.qrental.driver.domain.CallSign;
import ee.qrental.driver.domain.CallSignLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CallSignLinkBusinessRuleValidatorTest {
    private CallSignLinkBusinessRuleValidator instanceUnderTest;
    private CallSignLinkLoadPort loadPort;

    @BeforeEach
    void init() {
        loadPort = mock(CallSignLinkLoadPort.class);
        instanceUnderTest = new CallSignLinkBusinessRuleValidator(loadPort);
    }

    @Test
    public void testDomainValidateAdd() {
        // given
        final var driverIdToAdd = 5L;
        final var id = 6L;
        final var callSign = 4;

        final var callSignInstance = CallSign.builder()
                .id(id)
                .callSign(callSign)
                .comment("Test")
                .build();

        final var domain = CallSignLink.builder()
                .id(driverIdToAdd)
                .callSign(callSignInstance)
                .driverId(driverIdToAdd)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.of(2024, Month.NOVEMBER, 16))
                .comment("Test")
                .build();

        // when
        final var violationCollector = instanceUnderTest.validateAdd(domain);

        // then
        assertNotNull(callSignInstance);
        assertTrue(domain.isActive());
        assertEquals(domain.getId(), driverIdToAdd);
        assertEquals(domain.getDriverId(), driverIdToAdd);
        assertEquals(domain.getId(), driverIdToAdd);
        assertEquals(domain.getComment(), "Test");
        assertEquals(domain.getDateStart(), LocalDate.now());
        assertEquals(domain.getDateEnd(), LocalDate.of(2024, Month.NOVEMBER, 16));
        assertFalse(violationCollector.hasViolations());
    }

    @Test
    public void testDomainValidateAddCheckIsCallSignNotFree() {
        // given
        final var driverIdToAdd = 5L;
        final var id = 6L;
        final var callSign = 4;

        final var callSignInstance = CallSign.builder()
                .id(id)
                .callSign(callSign)
                .comment("Test")
                .build();

        final var domain = CallSignLink.builder()
                .id(driverIdToAdd)
                .callSign(callSignInstance)
                .driverId(driverIdToAdd)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.of(2024, Month.NOVEMBER, 16))
                .comment("Test")
                .build();

        when(loadPort.loadActiveByCallSignId(id)).thenReturn(domain);

        // when
        final var violationCollector = instanceUnderTest.validateAdd(domain);

        // then
        assertNotNull(callSignInstance);
        assertTrue(domain.isActive());
        assertEquals(domain.getId(), driverIdToAdd);
        assertEquals(domain.getDriverId(), driverIdToAdd);
        assertEquals(domain.getId(), driverIdToAdd);
        assertEquals(domain.getComment(), "Test");
        assertEquals(domain.getDateStart(), LocalDate.now());
        assertEquals(domain.getDateEnd(), LocalDate.of(2024, Month.NOVEMBER, 16));
        assertTrue(violationCollector.hasViolations());
        final var violation = violationCollector.getViolations().get(0);
        assertEquals("Call Sign: '4' already uses by active Link and can not be linked.", violation);
    }

    @Test
    public void testDomainValidateDelete() {
        // given
        final var driverIdToDelete = 5L;
        final var id = 6L;
        final var callSign = 4;

        final var callSignInstance = CallSign.builder()
                .id(id)
                .callSign(callSign)
                .comment("Test")
                .build();

        final var domain = CallSignLink.builder()
                .id(driverIdToDelete)
                .callSign(callSignInstance)
                .driverId(driverIdToDelete)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.of(2024, Month.NOVEMBER, 16))
                .comment("Test")
                .build();

        // when
        final var violationCollector = instanceUnderTest.validateDelete(id);

        // then
        assertNotNull(callSignInstance);
        assertTrue(domain.isActive());
        assertEquals(domain.getId(), driverIdToDelete);
        assertEquals(domain.getDriverId(), driverIdToDelete);
        assertEquals(domain.getId(), driverIdToDelete);
        assertEquals(domain.getComment(), "Test");
        assertEquals(domain.getDateStart(), LocalDate.now());
        assertEquals(domain.getDateEnd(), LocalDate.of(2024, Month.NOVEMBER, 16));
        assertFalse(violationCollector.hasViolations());
    }

    @Test
    public void testDomainValidateUpdate() {
        // given
        final var driverIdToUpdate = 5L;
        final var id = 6L;
        final var callSign = 4;

        final var callSignInstance = CallSign.builder()
                .id(id)
                .callSign(callSign)
                .comment("Test")
                .build();

        final var domain = CallSignLink.builder()
                .id(driverIdToUpdate)
                .callSign(callSignInstance)
                .driverId(driverIdToUpdate)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.of(2024, Month.NOVEMBER, 16))
                .comment("Test")
                .build();

        when(loadPort.loadById(driverIdToUpdate)).thenReturn(CallSignLink.builder().id(driverIdToUpdate).build());

        // when
        final var violationCollector = instanceUnderTest.validateUpdate(domain);

        // then
        assertNotNull(callSignInstance);
        assertTrue(domain.isActive());
        assertEquals(domain.getId(), driverIdToUpdate);
        assertEquals(domain.getDriverId(), driverIdToUpdate);
        assertEquals(domain.getId(), driverIdToUpdate);
        assertEquals(domain.getComment(), "Test");
        assertEquals(domain.getDateStart(), LocalDate.now());
        assertEquals(domain.getDateEnd(), LocalDate.of(2024, Month.NOVEMBER, 16));
        assertFalse(violationCollector.hasViolations());
    }

    @Test
    public void testDomainValidateUpdateCheckExistence() {
        // given
        final var driverIdToUpdate = 5L;
        final var id = 6L;
        final var callSign = 4;

        final var callSignInstance = CallSign.builder()
                .id(id)
                .callSign(callSign)
                .comment("Test")
                .build();

        final var domain = CallSignLink.builder()
                .id(driverIdToUpdate)
                .callSign(callSignInstance)
                .driverId(driverIdToUpdate)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.of(2024, Month.NOVEMBER, 16))
                .comment("Test")
                .build();

        when(loadPort.loadById(driverIdToUpdate)).thenReturn(null);

        // when
        final var violationCollector = instanceUnderTest.validateUpdate(domain);

        // then
        assertNotNull(callSignInstance);
        assertTrue(domain.isActive());
        assertEquals(domain.getId(), driverIdToUpdate);
        assertEquals(domain.getDriverId(), driverIdToUpdate);
        assertEquals(domain.getId(), driverIdToUpdate);
        assertEquals(domain.getComment(), "Test");
        assertEquals(domain.getDateStart(), LocalDate.now());
        assertEquals(domain.getDateEnd(), LocalDate.of(2024, Month.NOVEMBER, 16));
        assertTrue(violationCollector.hasViolations());
        final var violation = violationCollector.getViolations().get(0);
        assertEquals("Update of CallSign Link failed. No Record with id = 5", violation);
    }
}