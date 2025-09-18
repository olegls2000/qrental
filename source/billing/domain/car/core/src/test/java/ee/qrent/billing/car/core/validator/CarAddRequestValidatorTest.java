package ee.qrent.billing.car.core.validator;

import ee.qrent.billing.car.api.in.request.CarAddRequest;
import ee.qrent.billing.car.api.out.CarLoadPort;
import ee.qrent.billing.car.domain.Car;
import ee.qrent.common.core.validation.AttributeCheckerImpl;
import ee.qrent.common.in.validation.AttributeChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CarAddRequestValidatorTest {

  private CarAddRequestValidator instanceUnderTest;
  private CarLoadPort loadPort;
  private AttributeChecker attributeChecker;

  @BeforeEach
  void init() {
    loadPort = mock(CarLoadPort.class);
    attributeChecker = new AttributeCheckerImpl();
    instanceUnderTest = new CarAddRequestValidator(attributeChecker, loadPort);
  }

  private CarAddRequest getValidAddRequest() {
    final var addRequest = new CarAddRequest();
    addRequest.setActive(true);
    addRequest.setStatus("Status");
    addRequest.setQRent(TRUE);
    addRequest.setRegNumber("555RRR");
    addRequest.setVin("12345678901234567");
    addRequest.setReleaseDate(LocalDate.of(2024, Month.JANUARY, 15));
    addRequest.setManufacturer("Skoda");
    addRequest.setModel("Octavia");
    addRequest.setAppropriation(TRUE);
    addRequest.setElegance(TRUE);
    addRequest.setGearType("Automatic");
    addRequest.setFuelType("Petrol");
    addRequest.setLpg(TRUE);
    addRequest.setDateInstallLpg(LocalDate.of(2024, Month.JANUARY, 15).plus(30, ChronoUnit.DAYS));
    addRequest.setInsuranceFirm("Insurance Firm");
    addRequest.setInsuranceDateStart(LocalDate.of(2024, Month.JANUARY, 15));
    addRequest.setInsuranceDateEnd(LocalDate.of(2024, Month.JANUARY, 15).plusYears(1));
    addRequest.setSCard(TRUE);
    addRequest.setKey2(TRUE);
    addRequest.setGps(TRUE);
    addRequest.setTechnicalInspectionEnd(LocalDate.of(2024, Month.JANUARY, 15).plusYears(2));
    addRequest.setGasInspectionEnd(LocalDate.of(2024, Month.JANUARY, 15).plusYears(2));
    addRequest.setDateEndLpg(LocalDate.of(2024, Month.JANUARY, 15).plusYears(2));
    addRequest.setBrandingQrent(TRUE);
    addRequest.setBrandingBolt(TRUE);
    addRequest.setBrandingForus(TRUE);
    addRequest.setBrandingUber(TRUE);
    addRequest.setBrandingTallink(TRUE);
    addRequest.setCustomRentActive(TRUE);
    addRequest.setCustomRentAmount(BigDecimal.valueOf(200));
    addRequest.setComment("This is a comment");

    return addRequest;
  }

  @Test
  void testIfRegNumberIsNull() {
    // given
    final var addRequest = getValidAddRequest();
    addRequest.setRegNumber(null);

    // when
    final var violationCollector = instanceUnderTest.validate(addRequest);

    // then
    assertTrue(violationCollector.hasViolations());
    assertEquals(1, violationCollector.getViolations().size());
    assertTrue(
        violationCollector.getViolations().stream()
            .anyMatch(
                violation ->
                    violation.equals("Invalid value for Registration Number. Value must be set")));
  }

  @Test
  void testIfRegNumberHasInvalidFormat() {
    // given
    final var addRequestWithLess = getValidAddRequest();
    addRequestWithLess.setRegNumber("555RR");
    final var addRequestWithMore = getValidAddRequest();
    addRequestWithMore.setRegNumber("555RRRR");

    // when
    final var violationCollectorWithLess = instanceUnderTest.validate(addRequestWithLess);
    final var violationCollectorWithMore = instanceUnderTest.validate(addRequestWithMore);

    // then
    assertTrue(violationCollectorWithLess.hasViolations());
    assertEquals(1, violationCollectorWithLess.getViolations().size());
    assertTrue(
        violationCollectorWithLess.getViolations().stream()
            .anyMatch(
                violation ->
                    violation.equals(
                        "Invalid value for Registration Number. Current length: 5. Valid length must equal to: 6")));

    assertTrue(violationCollectorWithMore.hasViolations());
    assertEquals(1, violationCollectorWithMore.getViolations().size());
    assertTrue(
        violationCollectorWithMore.getViolations().stream()
            .anyMatch(
                violation ->
                    violation.equals(
                        "Invalid value for Registration Number. Current length: 7. Valid length must equal to: 6")));
  }

  @Test
  void testIfRegNumberIsNotUnique() {
    // given
    final var addRequest = getValidAddRequest();
    when(loadPort.loadByRegNumber(addRequest.getRegNumber()))
        .thenReturn(Car.builder().regNumber(addRequest.getRegNumber()).build());

    // when
    final var violationCollector = instanceUnderTest.validate(addRequest);

    // then
    assertTrue(violationCollector.hasViolations());
    assertEquals(1, violationCollector.getViolations().size());
    assertTrue(
        violationCollector.getViolations().stream()
            .anyMatch(
                violation ->
                    violation.equals("Car with mentioned Reg. Number 555RRR already exist")));
  }

  @Test
  void testIfVinIsNull() {
    // given
    final var addRequest = getValidAddRequest();
    addRequest.setVin(null);

    // when
    final var violationCollector = instanceUnderTest.validate(addRequest);

    // then
    assertTrue(violationCollector.hasViolations());
    assertEquals(1, violationCollector.getViolations().size());
    assertTrue(
        violationCollector.getViolations().stream()
            .anyMatch(violation -> violation.equals("Invalid value for VIN. Value must be set")));
  }

  @Test
  void testIfVinHasInvalidFormat() {
    // given
    final var addRequestWithLess = getValidAddRequest();
    addRequestWithLess.setVin("1234567890123456");
    final var addRequestWithMore = getValidAddRequest();
    addRequestWithMore.setVin("123456789012345678");

    // when
    final var violationCollectorWithLess = instanceUnderTest.validate(addRequestWithLess);
    final var violationCollectorWithMore = instanceUnderTest.validate(addRequestWithMore);

    // then
    assertTrue(violationCollectorWithLess.hasViolations());
    assertEquals(1, violationCollectorWithLess.getViolations().size());
    assertTrue(
        violationCollectorWithLess.getViolations().stream()
            .anyMatch(
                violation ->
                    violation.equals(
                        "Invalid value for VIN. Current length: 16. Valid length must equal to: 17")));

    assertTrue(violationCollectorWithMore.hasViolations());
    assertEquals(1, violationCollectorWithMore.getViolations().size());
    assertTrue(
        violationCollectorWithMore.getViolations().stream()
            .anyMatch(
                violation ->
                    violation.equals(
                        "Invalid value for VIN. Current length: 18. Valid length must equal to: 17")));
  }

  @Test
  void testIfVinIsNotUnique() {
    // given
    final var addRequest = getValidAddRequest();
    when(loadPort.loadByVin(addRequest.getVin()))
        .thenReturn(Car.builder().vin(addRequest.getVin()).build());

    // when
    final var violationCollector = instanceUnderTest.validate(addRequest);

    // then
    assertTrue(violationCollector.hasViolations());
    assertEquals(1, violationCollector.getViolations().size());
    assertTrue(
        violationCollector.getViolations().stream()
            .anyMatch(
                violation ->
                    violation.equals("Car with mentioned VIN 12345678901234567 already exist")));
  }
}
