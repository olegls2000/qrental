package ee.qrent.billing.driver.core.validator;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AttributeChecker;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.driver.api.out.DriverLoadPort;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.lang.String.format;
import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor
public abstract class AbstractDriverRequestValidator {

  private static final BigDecimal DECIMAL_MIN_OBLIGATION = BigDecimal.valueOf(1);
  private static final BigDecimal DECIMAL_MAX_OBLIGATION = BigDecimal.valueOf(1000);
  private static final int LENGTH_MAX_FIRST_NAME = 50;
  private static final int LENGTH_MAX_LAST_NAME = 50;
  private static final int LENGTH_FIXED_TAX_NUMBER = 11;
  private static final int LENGTH_FIXED_LHV_ACCOUNT = 20;
  private static final int LENGTH_MAX_ADDRESS = 100;
  private static final int LENGTH_MAX_DRIVER_LICENSE_NUMBER = 20;
  private static final int LENGTH_MAX_TAXI_LICENSE_NUMBER = 15;
  private static final int LENGTH_MIN_PHONE_NUMBER = 5;
  private static final int LENGTH_MAX_PHONE_NUMBER = 50;
  private static final int LENGTH_MAX_COMPANY_NAME = 50;
  private static final int LENGTH_MAX_REG_NUMBER = 15;
  private static final int LENGTH_MAX_VAT = 50;
  private static final int LENGTH_MAX_CEO_FIRST_NAME = 50;
  private static final int LENGTH_MAX_CEO_LAST_NAME = 50;
  private static final int LENGTH_MAX_COMPANY_ADDRESS = 120;
  private static final int LENGTH_MAX_COMMENT = 200;

  @Getter(PROTECTED)
  private final DriverLoadPort loadPort;

  private final AttributeChecker attributeChecker;

  @Getter(PROTECTED)
  private final QDateTime qDateTime;

  protected ViolationsCollector getViolationCollector() {
    return new ViolationsCollector();
  }

  protected void checkObligationNumber(
      final Boolean hasRequiredObligation,
      final BigDecimal attributeValue,
      final ViolationsCollector violationsCollector) {
    if (hasRequiredObligation) {
      final var attributeName = "Required Obligation";
      attributeChecker.checkRequired(attributeName, attributeValue, violationsCollector);

      if (attributeValue == null) {
        return;
      }
      attributeChecker.checkDecimalValueRange(
          attributeName,
          attributeValue,
          DECIMAL_MIN_OBLIGATION,
          DECIMAL_MAX_OBLIGATION,
          violationsCollector);
    }
  }

  protected void checkFirstName(
      final String attributeValue, final ViolationsCollector violationsCollector) {
    final var attributeName = "First name";
    attributeChecker.checkRequired(attributeName, attributeValue, violationsCollector);
    if (attributeValue == null) {
      return;
    }
    attributeChecker.checkStringLengthMax(
        attributeName, attributeValue, LENGTH_MAX_FIRST_NAME, violationsCollector);
  }

  protected void checkLastName(
      final String attributeValue, final ViolationsCollector violationsCollector) {
    final var attributeName = "Last name";
    attributeChecker.checkRequired(attributeName, attributeValue, violationsCollector);
    if (attributeValue == null) {
      return;
    }
    attributeChecker.checkStringLengthMax(
        attributeName, attributeValue, LENGTH_MAX_LAST_NAME, violationsCollector);
  }

  protected void checkTaxNumber(
      final Long attributeValue, final ViolationsCollector violationsCollector) {
    final var attributeName = "Isikukood";
    attributeChecker.checkRequired(attributeName, attributeValue, violationsCollector);
    if (attributeValue == null) {
      return;
    }
    attributeChecker.checkStringLengthFixed(
        attributeName, attributeValue, LENGTH_FIXED_TAX_NUMBER, violationsCollector);
  }

  protected void checkTaxNumberUniqueness(
      final Long taxNumber, final ViolationsCollector violationsCollector) {
    final var fromDb = loadPort.loadByTaxNumber(taxNumber);
    if (fromDb == null) {
      return;
    }
    violationsCollector.collect(
        format("Driver with mentioned Isikukood %d already exist", taxNumber));
  }

  protected void checkAddress(
      final String attributeValue, final ViolationsCollector violationsCollector) {
    final var attributeName = "Address";
    attributeChecker.checkRequired(attributeName, attributeValue, violationsCollector);
    if (attributeValue == null) {
      return;
    }
    attributeChecker.checkStringLengthMax(
        attributeName, attributeValue, LENGTH_MAX_ADDRESS, violationsCollector);
  }

  protected void checkLicenseNumber(
      final String attributeValue, final ViolationsCollector violationsCollector) {
    final var attributeName = "Driver License number";
    attributeChecker.checkRequired(attributeName, attributeValue, violationsCollector);
    if (attributeValue == null) {
      return;
    }
    attributeChecker.checkStringLengthMax(
        attributeName, attributeValue, LENGTH_MAX_DRIVER_LICENSE_NUMBER, violationsCollector);
  }

  protected void checkLicenseExpirationDate(
      final LocalDate attributeValue, final ViolationsCollector violationsCollector) {
    final var attributeName = "Driver License expiration date";
    attributeChecker.checkRequired(attributeName, attributeValue, violationsCollector);
    if (attributeValue == null) {
      return;
    }
    if (attributeValue.isBefore(qDateTime.getToday())) {
      violationsCollector.collect("License expiration date is in the past");
    }
  }

  protected void checkTaxiLicense(
      final String attributeValue, final ViolationsCollector violationsCollector) {
    final var attributeName = "Taxi license number";
    attributeChecker.checkRequired(attributeName, attributeValue, violationsCollector);
    if (attributeValue == null) {
      return;
    }
    attributeChecker.checkStringLengthMax(
        attributeName, attributeValue, LENGTH_MAX_TAXI_LICENSE_NUMBER, violationsCollector);
  }

  protected void checkPhoneNumber(
      final String attributeValue, final ViolationsCollector violationsCollector) {
    final var attributeName = "Phone number(s)";
    attributeChecker.checkRequired(attributeName, attributeValue, violationsCollector);
    if (attributeValue == null) {
      return;
    }
    attributeChecker.checkStringLengthRange(
        attributeName,
        attributeValue,
        LENGTH_MIN_PHONE_NUMBER,
        LENGTH_MAX_PHONE_NUMBER,
        violationsCollector);
  }

  protected void checkEmail(
      final String attributeValue, final ViolationsCollector violationsCollector) {
    final var attributeName = "Email";
    attributeChecker.checkRequired(attributeName, attributeValue, violationsCollector);
    if (attributeValue == null) {
      return;
    }
    checkEmailPattern(attributeValue, violationsCollector);
  }

  private void checkEmailPattern(
      final String email, final ViolationsCollector violationsCollector) {
    final var emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    if (email.matches(emailRegex)) {

      return;
    }
    violationsCollector.collect("Invalid email pattern,please follow the example: email@gmail.com");
  }

  protected void checkCompanyName(
      final String attributeValue, final ViolationsCollector violationsCollector) {
    final var attributeName = "Company name";
    attributeChecker.checkRequired(attributeName, attributeValue, violationsCollector);
    attributeChecker.checkStringLengthMax(
        attributeName, attributeValue, LENGTH_MAX_COMPANY_NAME, violationsCollector);
  }

  protected void checkRegistrationNumber(
      final String attributeValue, final ViolationsCollector violationsCollector) {
    final var attributeName = "Registration number";
    attributeChecker.checkRequired(attributeName, attributeValue, violationsCollector);
    attributeChecker.checkStringLengthMax(
        attributeName, attributeValue, LENGTH_MAX_REG_NUMBER, violationsCollector);
  }

  protected void checkCompanyVat(
      final String attributeValue, final ViolationsCollector violationsCollector) {
    attributeChecker.checkStringLengthMax(
        "Company VAT number", attributeValue, LENGTH_MAX_VAT, violationsCollector);
  }

  protected void checkCeoFirstName(
      final String attributeValue, final ViolationsCollector violationsCollector) {
    final var attributeName = "CEO First name";
    attributeChecker.checkRequired(attributeName, attributeValue, violationsCollector);
    if (attributeValue == null) {
      return;
    }
    attributeChecker.checkStringLengthMax(
        attributeName, attributeValue, LENGTH_MAX_CEO_FIRST_NAME, violationsCollector);
  }

  protected void checkCeoLastName(
      final String attributeValue, final ViolationsCollector violationsCollector) {
    final var attributeName = "CEO Last name";
    attributeChecker.checkRequired(attributeName, attributeValue, violationsCollector);
    if (attributeValue == null) {
      return;
    }
    attributeChecker.checkStringLengthMax(
        attributeName, attributeValue, LENGTH_MAX_CEO_LAST_NAME, violationsCollector);
  }

  protected void checkCeoTaxNumber(
      final Long attributeValue, final ViolationsCollector violationsCollector) {
    final var attributeName = "CEO Isikukood";
    attributeChecker.checkRequired(attributeName, attributeValue, violationsCollector);
    if (attributeValue == null) {
      return;
    }
    attributeChecker.checkStringLengthFixed(
        attributeName, attributeValue, LENGTH_FIXED_TAX_NUMBER, violationsCollector);
  }

  protected void checkCompanyAddress(
      final String attributeValue, final ViolationsCollector violationsCollector) {
    final var attributeName = "Company address";
    attributeChecker.checkRequired(attributeName, attributeValue, violationsCollector);
    attributeChecker.checkStringLengthMax(
        attributeName, attributeValue, LENGTH_MAX_COMPANY_ADDRESS, violationsCollector);
  }

  protected void checkComment(
      final String attributeValue, final ViolationsCollector violationsCollector) {
    attributeChecker.checkStringLengthMax(
        "Comment", attributeValue, LENGTH_MAX_COMMENT, violationsCollector);
  }

  protected void checkLhvAccount(
          final String attributeValue, final ViolationsCollector violationsCollector) {
    final var attributeName = "LHV Account";
    attributeChecker.checkRequired(attributeName, attributeValue, violationsCollector);
    attributeChecker.checkStringLengthFixed(
            attributeName, attributeValue, LENGTH_FIXED_LHV_ACCOUNT, violationsCollector);
  }
}
