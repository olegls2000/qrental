package ee.qrent.billing.driver.core.validator;

import ee.qrent.billing.driver.core.validator.DriverAddRequestValidator;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AttributeChecker;
import ee.qrent.common.core.validation.AttributeCheckerImpl;
import ee.qrent.billing.driver.api.in.request.DriverAddRequest;
import ee.qrent.billing.driver.api.out.DriverLoadPort;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DriverAddRequestValidatorTest {

  private DriverAddRequestValidator instanceUnderTest;
  private DriverLoadPort loadPort;

  private QDateTime qDateTime;
  private AttributeChecker attributeChecker;

  @BeforeEach
  void init() {
    loadPort = mock(DriverLoadPort.class);
    qDateTime = mock(QDateTime.class);
    attributeChecker = new AttributeCheckerImpl();
    instanceUnderTest = new DriverAddRequestValidator(attributeChecker, loadPort, qDateTime);
    when(qDateTime.getToday()).thenReturn(LocalDate.of(2025, Month.JANUARY, 15));
  }

  private DriverAddRequest getValidDriverAddRequest() {
    final var addRequest = new DriverAddRequest();
    addRequest.setActive(true);
    addRequest.setHasRequiredObligation(Boolean.FALSE);
    addRequest.setFirstName("John");
    addRequest.setLastName("Doe");
    addRequest.setTaxNumber(48108259011L);
    addRequest.setAddress("Tallinn, Str. Lootsa, 45b");
    addRequest.setDriverLicenseNumber("AR-TY_45879");
    addRequest.setDriverLicenseExp(qDateTime.getToday().plus(2, ChronoUnit.DAYS));
    addRequest.setTaxiLicense("TLL-T-958");
    addRequest.setPhone("+3729876587, +383050478955");
    addRequest.setEmail("test.f.l@gmail.com");
    addRequest.setCompanyName("Driver&Company");
    addRequest.setRegNumber("REG-7878");
    addRequest.setCompanyAddress("Tallinn, Str. Lootsa, 88c");
    addRequest.setCompanyVat("VAT-8989");
    addRequest.setCompanyCeoFirstName("CEO_FN");
    addRequest.setCompanyCeoLastName("CEO_LN");
    addRequest.setCompanyCeoTaxNumber(48108259011L);
    addRequest.setComment("This is a comment");

    return addRequest;
  }

  //@Test
  void testObligationNumberIfObligationRequiredButNotSet() {
    // given
    final var addRequest = getValidDriverAddRequest();
    addRequest.setHasRequiredObligation(true);
    addRequest.setRequiredObligation(null);

    // when
    final var violationCollector = instanceUnderTest.validate(addRequest);

    // then
    assertTrue(violationCollector.hasViolations());
    assertEquals(1, violationCollector.getViolations().size());
    assertTrue(
        violationCollector.getViolations().stream()
            .anyMatch(
                violation ->
                    violation.equals("Invalid value for Required Obligation. Value must be set")));
  }

  //@Test
  void testObligationNumberIfObligationRequiredAndNotInRange() {
    // given
    final var addRequest = getValidDriverAddRequest();
    addRequest.setHasRequiredObligation(true);
    addRequest.setRequiredObligation(BigDecimal.valueOf(1001));

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
                        "Invalid value for Required Obligation. Valid value must be in a range: [1 ... 1000]")));
  }

  //@Test
  void testIfFirstNameNotSet() {
    // given
    final var addRequest = getValidDriverAddRequest();
    addRequest.setFirstName(null);

    // when
    final var violationCollector = instanceUnderTest.validate(addRequest);

    // then
    assertTrue(violationCollector.hasViolations());
    assertEquals(1, violationCollector.getViolations().size());
    assertTrue(
        violationCollector.getViolations().stream()
            .anyMatch(
                violation -> violation.equals("Invalid value for First name. Value must be set")));
  }

  //@Test
  void testIfFirstNameTooLong() {
    // given
    final var addRequest = getValidDriverAddRequest();
    addRequest.setFirstName("51_character_123456789_123456789_123456789_12345678");

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
                        "Invalid value for First name. Current length: 51. Valid length must be not more then: 50")));
  }

  //@Test
  void testIfLastNameNotSet() {
    // given
    final var addRequest = getValidDriverAddRequest();
    addRequest.setLastName(null);

    // when
    final var violationCollector = instanceUnderTest.validate(addRequest);

    // then
    assertTrue(violationCollector.hasViolations());
    assertEquals(1, violationCollector.getViolations().size());
    assertTrue(
        violationCollector.getViolations().stream()
            .anyMatch(
                violation -> violation.equals("Invalid value for Last name. Value must be set")));
  }

  //@Test
  void testIfLastNameTooLong() {
    // given
    final var addRequest = getValidDriverAddRequest();
    addRequest.setLastName("51_character_123456789_123456789_123456789_12345678");

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
                        "Invalid value for Last name. Current length: 51. Valid length must be not more then: 50")));
  }

  //@Test
  void testIfTaxNumberNotSet() {
    // given
    final var addRequest = getValidDriverAddRequest();
    addRequest.setTaxNumber(null);

    // when
    final var violationCollector = instanceUnderTest.validate(addRequest);

    // then
    assertTrue(violationCollector.hasViolations());
    assertEquals(1, violationCollector.getViolations().size());
    assertTrue(
        violationCollector.getViolations().stream()
            .anyMatch(
                violation -> violation.equals("Invalid value for Isikukood. Value must be set")));
  }

  //@Test
  void testIfTaxNumberTooLong() {
    // given
    final var addRequest = getValidDriverAddRequest();
    addRequest.setTaxNumber(123456789123L);

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
                        "Invalid value for Isikukood. Current length: 12. Valid length must equal to: 11")));
  }
}
