package ee.qrent.billing.car.core.validator;

import ee.qrent.billing.car.api.out.CarLoadPort;
import ee.qrent.common.in.validation.AttributeChecker;
import ee.qrent.common.in.validation.ViolationsCollector;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

import static java.lang.String.format;
import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor
public abstract class AbstractCarRequestValidator {

  private static final int LENGTH_MAX_REGISTRATION_NUMBER = 6;
  private static final int LENGTH_FIXED_VI_NUMBER = 17;
  private static final BigDecimal DECIMAL_MIN_CUSTOM_RENT_AMOUNT = BigDecimal.valueOf(1);
  private static final BigDecimal DECIMAL_MAX_CUSTOM_RENT_AMOUNT = BigDecimal.valueOf(1000);

  @Getter(PROTECTED)
  private final CarLoadPort loadPort;

  private final AttributeChecker attributeChecker;

  protected ViolationsCollector getViolationCollector() {
    return new ViolationsCollector();
  }

  protected void checkRegNumber(
      final String attributeValue, final ViolationsCollector violationsCollector) {
    final var attributeName = "Registration Number";
    attributeChecker.checkRequired(attributeName, attributeValue, violationsCollector);
    if (attributeValue == null) {

      return;
    }
    attributeChecker.checkStringLengthFixed(
        attributeName, attributeValue, LENGTH_MAX_REGISTRATION_NUMBER, violationsCollector);
  }

  protected void checkVin(
      final String attributeValue, final ViolationsCollector violationsCollector) {
    final var attributeName = "VIN";
    attributeChecker.checkRequired(attributeName, attributeValue, violationsCollector);
    if (attributeValue == null) {

      return;
    }
    attributeChecker.checkStringLengthFixed(
        attributeName, attributeValue, LENGTH_FIXED_VI_NUMBER, violationsCollector);
  }

  protected void checkRegNumberUniqueness(
      final String regNumber, final ViolationsCollector violationsCollector) {
    final var fromDb = loadPort.loadByRegNumber(regNumber);
    if (fromDb == null) {

      return;
    }
    violationsCollector.collect(
        format("Car with mentioned Reg. Number %s already exist", regNumber));
  }

  protected void checkVinUniqueness(
      final String vin, final ViolationsCollector violationsCollector) {
    final var fromDb = loadPort.loadByVin(vin);
    if (fromDb == null) {

      return;
    }
    violationsCollector.collect(format("Car with mentioned VIN %s already exist", vin));
  }

    protected void checkBoltIdentifierUniqueness(
            final String boltIdentifier, final ViolationsCollector violationsCollector) {
        final var fromDb = loadPort.loadByBoltIdentifier(boltIdentifier);
        if (fromDb == null) {

            return;
        }
        violationsCollector.collect(format("Car with mentioned Bolt Identifier %s already exist", boltIdentifier));
    }

  protected void validateCustomRentAmount(
      final Boolean customRentActive,
      final BigDecimal customRentAmount,
      final ViolationsCollector violationsCollector) {

    if (customRentActive != null && customRentActive) {
      final var attributeName = "Custom Rent Amount";
      attributeChecker.checkRequired(attributeName, customRentAmount, violationsCollector);
      attributeChecker.checkDecimalValueRange(
          attributeName,
          customRentAmount,
          DECIMAL_MIN_CUSTOM_RENT_AMOUNT,
          DECIMAL_MAX_CUSTOM_RENT_AMOUNT,
          violationsCollector);
    }
  }
}
