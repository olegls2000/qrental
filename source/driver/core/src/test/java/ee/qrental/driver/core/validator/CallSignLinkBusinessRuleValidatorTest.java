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
        final var idToAdd = 6L;
        final var callSignId = 4;

        final var callSign = CallSign.builder()
                .id(idToAdd)
                .callSign(callSignId)
                .comment("Test")
                .build();

        final var domain = CallSignLink.builder()
                .id(driverIdToAdd)
                .callSign(callSign)
                .driverId(driverIdToAdd)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.of(2024, Month.NOVEMBER, 16))
                .comment("Test")
                .build();

        // when
        final var violationCollector = instanceUnderTest.validateAdd(domain);

        // then
        assertNotNull(callSign);
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
        final var idToAdd = 6L;
        final var callSignId = 4;

        final var callSign = CallSign.builder()
                .id(idToAdd)
                .callSign(callSignId)
                .comment("Test")
                .build();

        final var domain = CallSignLink.builder()
                .id(driverIdToAdd)
                .callSign(callSign)
                .driverId(driverIdToAdd)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.of(2024, Month.NOVEMBER, 16))
                .comment("Test")
                .build();

        when(loadPort.loadActiveByCallSignId(idToAdd)).thenReturn(domain);

        // when
        final var violationCollector = instanceUnderTest.validateAdd(domain);

        // then
        assertNotNull(callSign);
        assertTrue(domain.isActive());
        assertEquals(domain.getId(), driverIdToAdd);
        assertEquals(domain.getDriverId(), driverIdToAdd);
        assertEquals(domain.getId(), driverIdToAdd);
        assertEquals(domain.getComment(), "Test");
        assertEquals(domain.getDateStart(), LocalDate.now());
        assertEquals(domain.getDateEnd(), LocalDate.of(2024, Month.NOVEMBER, 16));
        assertTrue(violationCollector.hasViolations());
    }

    @Test
    public void testDomainValidateDelete() {
        // given
        final var driverIdToAdd = 5L;
        final var idToAdd = 6L;
        final var callSignId = 4;

        final var callSign = CallSign.builder()
                .id(idToAdd)
                .callSign(callSignId)
                .comment("Test")
                .build();

        final var domain = CallSignLink.builder()
                .id(driverIdToAdd)
                .callSign(callSign)
                .driverId(driverIdToAdd)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.of(2024, Month.NOVEMBER, 16))
                .comment("Test")
                .build();

        // when
        final var violationCollector = instanceUnderTest.validateDelete(idToAdd);

        // then
        assertNotNull(callSign);
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
    public void testDomainValidateUpdate() {
        // given
        final var driverIdToAdd = 5L;
        final var idToAdd = 6L;
        final var callSignId = 4;

        final var callSign = CallSign.builder()
                .id(idToAdd)
                .callSign(callSignId)
                .comment("Test")
                .build();

        final var domain = CallSignLink.builder()
                .id(driverIdToAdd)
                .callSign(callSign)
                .driverId(driverIdToAdd)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.of(2024, Month.NOVEMBER, 16))
                .comment("Test")
                .build();

        when(loadPort.loadById(driverIdToAdd)).thenReturn(CallSignLink.builder().id(driverIdToAdd).build());

        // when
        final var violationCollector = instanceUnderTest.validateUpdate(domain);

        // then
        assertNotNull(callSign);
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
    public void testDomainValidateUpdateCheckExistence() {
        // given
        final var driverIdToAdd = 5L;
        final var idToAdd = 6L;
        final var callSignId = 4;

        final var callSign = CallSign.builder()
                .id(idToAdd)
                .callSign(callSignId)
                .comment("Test")
                .build();

        final var domain = CallSignLink.builder()
                .id(driverIdToAdd)
                .callSign(callSign)
                .driverId(driverIdToAdd)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.of(2024, Month.NOVEMBER, 16))
                .comment("Test")
                .build();

        when(loadPort.loadById(driverIdToAdd)).thenReturn(null);

        // when
        final var violationCollector = instanceUnderTest.validateUpdate(domain);

        // then
        assertNotNull(callSign);
        assertTrue(domain.isActive());
        assertEquals(domain.getId(), driverIdToAdd);
        assertEquals(domain.getDriverId(), driverIdToAdd);
        assertEquals(domain.getId(), driverIdToAdd);
        assertEquals(domain.getComment(), "Test");
        assertEquals(domain.getDateStart(), LocalDate.now());
        assertEquals(domain.getDateEnd(), LocalDate.of(2024, Month.NOVEMBER, 16));
        assertTrue(violationCollector.hasViolations());
    }
}